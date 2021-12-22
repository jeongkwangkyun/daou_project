package com.daou.project.model.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daou.proejct.exception.CouponException;
import com.daou.proejct.exception.PointException;
import com.daou.proejct.exception.SavemoneyException;
import com.daou.project.Enum.Coupon;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentMapper paymentMapper;

	public UserDataDto listUserData(long userNo) throws Exception {
		UserDataDto userDataDto = new UserDataDto(getPoint(userNo), getCoupon(userNo), getSaveMoney(userNo));
		return userDataDto;
	}

	public List<PointDto> getPoint(long userNo) throws Exception{
		return paymentMapper.getPoint(userNo);
	}

	public List<CouponDto> getCoupon(long userNo) throws Exception {
		return paymentMapper.getCoupon(userNo);
	}

	public List<CouponDto> getCouponNoDuplicated(long userNo) throws Exception {
		return paymentMapper.getCouponNoDuplicated(userNo);
	}

	public SaveMoneyDto getSaveMoney(long userNo) throws Exception {
		return  paymentMapper.getSaveMoney(userNo);
	}

	@Transactional
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception {
		
		UserDataDto userDataDto = new UserDataDto(getPoint(reqPaymentDto.getUserNo()),
												  getCouponNoDuplicated(reqPaymentDto.getUserNo()),
												  getSaveMoney(reqPaymentDto.getUserNo()));
		
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setUserNo(reqPaymentDto.getUserNo());

		//1. 임시 Payment 테이블 등록하기 
		insertPayment(paymentDto);

		//2. Coupon 테이블 최신화
		updateCouponTable(userDataDto.getCouponList(),reqPaymentDto, paymentDto);

		//3. Point 테이블, Payment_Point 최신화
		updatePointTable(userDataDto.getPointList(), reqPaymentDto, paymentDto);

		//4. savemoney 테이블 최신화
		updateSavemoneyTable(userDataDto.getSavemoneyList(), reqPaymentDto, paymentDto);

		//5. 테이블 최신화
		updatePaymentTable(paymentDto,reqPaymentDto);

		//6. product_payment 테이블 등록
		insertPayProductTable(new PayProductDto(reqPaymentDto.getProductNo(),paymentDto.getPayNo(),reqPaymentDto.getProductCnt()));
		
		return true;
	}

	@Transactional
	private void insertPayProductTable(PayProductDto payProductDto) throws SQLException {
		paymentMapper.insertPayProductTable(payProductDto);
	}

	@Transactional
	private void updatePaymentTable(PaymentDto paymentDto, RequestPaymentDto reqPaymentDto) throws SQLException {
		paymentDto.setPayment(reqPaymentDto.getTotalPrice(), reqPaymentDto.getUserMoney(), 'n');
		paymentMapper.updatePaymentTable(paymentDto);
	}

	@Transactional
	private void updateSavemoneyTable(SaveMoneyDto savemoneyList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		long savemoneyNo = 1;
		if(reqPaymentDto.getPaySaveMoney() > savemoneyList.getSaveMoney()) {
			throw new SavemoneyException();
		}
		if (reqPaymentDto.getPaySaveMoney() !=0) {
			savemoneyNo = savemoneyList.getSavemoneyNo();
			paymentDto.setUseSavemoney(reqPaymentDto.getPaySaveMoney());

			savemoneyList.setSaveMoney(savemoneyList.getSaveMoney() - reqPaymentDto.getPaySaveMoney());
			paymentMapper.updateSavemoney(savemoneyList);

		}
		paymentDto.setSavemoneyNo(savemoneyNo);

	}

	@Transactional
	private void updatePointTable(List<PointDto> pointList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		int requestPoint = reqPaymentDto.getPayPoint();
		if(requestPoint > 0) {
			int userAllPoint = pointList.stream().mapToInt(PointDto::getBalancePoint).sum();
			
			if(requestPoint > userAllPoint) {
				throw new PointException();
			}
			
			int listSize = pointList.size();
			for(int i = 0 ; i < listSize ; i++) {
				PointDto updatePoint = pointList.get(i);

				int usablePoint = updatePoint.getBalancePoint();

				PayPointDto registPoint = PayPointDto.builder()
						.pointNo(updatePoint.getPointNo())
						.payNo(paymentDto.getPayNo())
						.userNo(paymentDto.getUserNo())
						.build();

				if(requestPoint > usablePoint) {	
					updatePoint.setBalancePoint(0);
					registPoint.setUsePoint(usablePoint);
					change_state(updatePoint,registPoint);
					requestPoint -= usablePoint;
				}
				else {
					updatePoint.setBalancePoint(usablePoint - requestPoint);
					registPoint.setUsePoint(requestPoint);
					change_state(updatePoint,registPoint);
					requestPoint = 0;
					break;
				}
			}
		}
	}

	@Transactional
	private void change_state(PointDto updatePoint, PayPointDto registPoint) throws SQLException {
		paymentMapper.updatePointTable(updatePoint);
		paymentMapper.insertPayPointTable(registPoint);
	}

	@Transactional
	private void updateCouponTable(List<CouponDto> couponList, RequestPaymentDto reqPaymentDto,PaymentDto paymentDto) throws SQLException {
		long couponNo = 1;
		if(reqPaymentDto.getPayCoupon() != 0) {
			int listSize = couponList.size();
			for(int i = 0 ; i<listSize; i++) {
				if(couponList.get(i).getType()==reqPaymentDto.getPayCoupon()) {
					couponNo=couponList.get(i).getCouponNo();
					paymentMapper.updateUsedCoupon(couponNo);
					break;
				}
			}
		}
		paymentDto.setCouponNo(couponNo);
	}
	
	@Transactional
	public int insertPayment(PaymentDto paymentDto) throws Exception{
		return paymentMapper.insertPayment(paymentDto);
	}


	public RequestPaymentDto getAutoPayment(RequestPaymentDto orderDto) throws Exception {
		orderDto.setUserMoney(orderDto.getTotalPrice());
		
		applyCoupon(orderDto);
		
		applyPoint(orderDto);
		
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

	private void applyPoint(RequestPaymentDto orderDto) throws SQLException {
		int point = userEnablePoint(orderDto.getUserNo());

		int userMoney = orderDto.getUserMoney();
		if(userMoney > point) {
			userMoney -= point;
			orderDto.setPayPoint(point);
			orderDto.setUserMoney(userMoney);
		}
		else {
			orderDto.setPayPoint(userMoney);
			orderDto.setUserMoney(0);
		}
	}

	private void applyCoupon(RequestPaymentDto orderDto) throws Exception {
		int userMoney = orderDto.getUserMoney();

		if(orderDto.getTotalPrice() >= Coupon.PERCENT5.getPrice()) {			
			List<CouponDto> couponList = getCouponNoDuplicated(orderDto.getUserNo());
			int coupon = 0;
			int listSize = couponList.size();
			
			if(listSize !=0) {
				if(orderDto.getTotalPrice() >= Coupon.PERCENT20.getPrice()) {
					coupon = couponList.get(0).getType();
				}
				else {				
					int coupon10 = 0;
					int coupon5 = 0;
					for(int i = 0 ; i < listSize ; i++) {
						
						if(couponList.get(i).getType() == Coupon.PERCENT10.getDiscountPercent()) {
							coupon10 += 1;
						}
						else if(couponList.get(i).getType() == Coupon.PERCENT5.getDiscountPercent()) {
							coupon5 += 1;
						}
					}
					if(orderDto.getTotalPrice()>=Coupon.PERCENT10.getPrice()) {
						if(coupon10 != 0 ) {
							coupon = Coupon.PERCENT10.getDiscountPercent(); 
						}
						else if(coupon5 != 0 ) {
							coupon = Coupon.PERCENT5.getDiscountPercent();
						}
					}
					else if(coupon5 != 0){
						coupon = Coupon.PERCENT5.getDiscountPercent();
					}
				}
			}
			orderDto.setPayCoupon(coupon);
			userMoney = userMoney - userMoney * coupon /100; 
			orderDto.setUserMoney(userMoney);
		}
	}

	private int userEnablePoint(long userNo) throws SQLException {
		return paymentMapper.userEnablePoint(userNo); 
	}

}
