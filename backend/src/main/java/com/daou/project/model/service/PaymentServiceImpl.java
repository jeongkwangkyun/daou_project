package com.daou.project.model.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PayPointDto;
import com.daou.project.model.PayProductDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentMapper paymentMapper;

	@Override
	public UserDataDto listUserData(long userNo) throws Exception {
		UserDataDto userDataDto = new UserDataDto(getPoint(userNo), getCoupon(userNo), getSaveMoney(userNo));
		return userDataDto;
	}


	public List<PointDto> getPoint(long userNo) throws Exception{
		return paymentMapper.getPoint(userNo);
	}


	@Override
	public List<CouponDto> getCoupon(long userNo) throws Exception {
		return paymentMapper.getCoupon(userNo);
	}

	@Override
	public List<CouponDto> getCouponNoDuplicated(long userNo) throws Exception {
		return paymentMapper.getCouponNoDuplicated(userNo);
	}
	@Override
	public SaveMoneyDto getSaveMoney(long userNo) throws Exception {
		return  paymentMapper.getSaveMoney(userNo);
	}


	@Override
	@Transactional
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception {
		UserDataDto userDataDto = new UserDataDto(getPoint(reqPaymentDto.getUserNo()), getCouponNoDuplicated(reqPaymentDto.getUserNo()), getSaveMoney(reqPaymentDto.getUserNo()));

		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setUserNo(reqPaymentDto.getUserNo());

		//1. 임시 Payment 테이블 등록하기 
		registerTempTable(paymentDto);

		//2. Coupon 테이블 최신화
		paymentDto = registerCouponTable(userDataDto.getCouponList(),reqPaymentDto, paymentDto);

		//3. Point 테이블, Payment_Point 최신화
		registerPointTable(userDataDto.getPointList(), reqPaymentDto, paymentDto);
		
		//4. savemoney 테이블 최신화
		paymentDto = registerSavemoneyTable(userDataDto.getSavemoneyList(), reqPaymentDto, paymentDto);
		
		//5. 테이블 최신화
		updatePaymentTable(paymentDto,reqPaymentDto);
		
		//6. product_payment 테이블 등록
		insertPayProducTable(new PayProductDto(reqPaymentDto.getProductNo(),paymentDto.getPayNo(),reqPaymentDto.getProductCnt()));
		
		return true;
	}


	private void insertPayProducTable(PayProductDto payProductDto) throws SQLException {
		paymentMapper.insertPayProducTable(payProductDto);
	}


	private void updatePaymentTable(PaymentDto paymentDto, RequestPaymentDto reqPaymentDto) throws SQLException {
		paymentDto.setRefundYn('n');
		paymentDto.setTotalPrice(reqPaymentDto.getTotalPrice());
		paymentDto.setUserMoney(reqPaymentDto.getUserMoney());
		
		paymentMapper.updatePaymentTable(paymentDto);
	}


	private PaymentDto registerSavemoneyTable(SaveMoneyDto savemoneyList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		long savemoneyNo = 1;
		if (reqPaymentDto.getPaySaveMoney() !=0) {
			savemoneyNo = savemoneyList.getSavemoneyNo();
			paymentDto.setUseSavemoney(reqPaymentDto.getPaySaveMoney());
			
			savemoneyList.setSaveMoney(savemoneyList.getSaveMoney() - reqPaymentDto.getPaySaveMoney());
			paymentMapper.updateSavemoney(savemoneyList);
			
		}
		paymentDto.setSavemoneyNo(savemoneyNo);
		
		return paymentDto;
	}


	private void registerPointTable(List<PointDto> pointList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		int remainPoint = reqPaymentDto.getPayPoint();

		if(remainPoint > 0) {
			int listSize = pointList.size();
			for(int i = 0 ; i < listSize; i++) {
				PointDto updatePoint = pointList.get(i);
				int usePoint = 0;
				
				usePoint = updatePoint.getBalancePoint();
				
				PayPointDto registPoint = PayPointDto.builder()
						.pointNo(updatePoint.getPointNo())
						.payNo(paymentDto.getPayNo())
						.userNo(paymentDto.getUserNo())
						.usePoint(usePoint)
						.build();
				
				if(remainPoint>updatePoint.getBalancePoint()) {	
					updatePoint.setBalancePoint(0);
					paymentMapper.updatePointTable(updatePoint);
					paymentMapper.insertPayPointTable(registPoint);
					remainPoint -= usePoint;
				}
				else {
					updatePoint.setBalancePoint(usePoint - remainPoint);
					paymentMapper.updatePointTable(updatePoint);
					paymentMapper.insertPayPointTable(registPoint);
					break;
				}
			}
		}

	}

	private PaymentDto registerCouponTable(List<CouponDto> couponList, RequestPaymentDto reqPaymentDto,PaymentDto paymentDto) throws SQLException {
		long couponNo = 1;
		if(reqPaymentDto.getPayCoupon() != 0) {
			int listSize = couponList.size();
			for(int i = 0 ; i<listSize; i++) {
				if(couponList.get(i).getType()==reqPaymentDto.getPayCoupon()) {
					couponNo=couponList.get(i).getCouponNo();
					paymentMapper.updateCoupon(couponNo);
					break;
				}
			}
		}
		paymentDto.setCouponNo(couponNo);
		return paymentDto;
	}


	public int registerTempTable(PaymentDto paymentDto) throws Exception{
		return paymentMapper.registerTempTable(paymentDto);
	}


	@Override
	public RequestPaymentDto getAutoPayment(RequestPaymentDto orderDto) throws Exception {
		orderDto.setUserMoney(orderDto.getTotalPrice());
		
		orderDto = applyCoupon(orderDto);
		
		orderDto = applyPoint(orderDto);
		
		if(orderDto.getUserMoney() != 0 ) {
			orderDto = applySavemoney(orderDto);
		}
		return orderDto;
	}

	private RequestPaymentDto applySavemoney(RequestPaymentDto orderDto) throws Exception {
		int savemoney = getSaveMoney(orderDto.getUserNo()).getSaveMoney();
		
		int userMoney = orderDto.getUserMoney();
		if(userMoney > savemoney) {
			orderDto.setUserMoney(userMoney-savemoney);
			userMoney -= savemoney;
			orderDto.setPaySaveMoney(savemoney);
		}
		else {
			orderDto.setPaySaveMoney(userMoney);
			orderDto.setUserMoney(0);
		}
		
		return orderDto;
	}


	private RequestPaymentDto applyPoint(RequestPaymentDto orderDto) throws SQLException {
		int point = sumPoint(orderDto.getUserNo());
		
		int userMoney = orderDto.getUserMoney();
		if(userMoney > point) {
			userMoney -= point;
			orderDto.setPayPoint(point);
			orderDto.setUserMoney(userMoney);
		}
		else {
			orderDto.setPayPoint(userMoney);
			orderDto.setUserMoney(0);
			return orderDto;
		}
		return orderDto;
	}


	private RequestPaymentDto applyCoupon(RequestPaymentDto orderDto) throws Exception {
		int userMoney = orderDto.getUserMoney();
		
		if(orderDto.getTotalPrice() >= 10000) {			
			List<CouponDto> couponList = getCouponNoDuplicated(orderDto.getUserNo());
			int coupon = 0;
			// 쿠폰 가격별 로직 처리해줘야함
			if(couponList.size() !=0) {
				if(orderDto.getTotalPrice() >= 30000) {
					coupon = couponList.get(0).getType();
				}
				else {					
					int couponA = 0;
					int couponB = 0;
					int couponC = 0;
					int listSize = couponList.size();
					for(int i = 0 ; i < listSize ; i++) {
						if(couponList.get(i).getType() == 20) {
							couponA +=1;
						}
						else if(couponList.get(i).getType() == 10) {
							couponB += 1;
						}
						else if(couponList.get(i).getType() == 5) {
							couponC += 1;
						}
					}
					if(orderDto.getTotalPrice()>=20000) {
						if(couponB != 0 ) {
							coupon = 10;
						}
						else if(couponC != 0 ) {
							coupon = 5;
						}
					}
					else if(couponC != 0){
						coupon = 5;
					}
				}
			}
			orderDto.setPayCoupon(coupon);
			userMoney = userMoney - userMoney * coupon /100; 
			orderDto.setUserMoney(userMoney);
		}
		return orderDto;
	}


	private int sumPoint(long userNo) throws SQLException {
		return paymentMapper.sumPoint(userNo); 
	}

}
