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
import com.daou.proejct.exception.UsermoneyException;
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
		
		LinkedList<PayPointDto> payPoint = new LinkedList<PayPointDto>();
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setUserNo(reqPaymentDto.getUserNo());
		
		//1. 쿠폰 테이블 update
		updateCouponTable(userDataDto.getCouponList(), reqPaymentDto, paymentDto);

		//2. 포인 테이블 update
		updatePointTable(userDataDto.getPointList(), reqPaymentDto, paymentDto, payPoint);

		//3. 적립금 테이블 update
		updateSavemoneyTable(userDataDto.getSavemoneyList(), reqPaymentDto, paymentDto);

		//4. 결제 테이블 insert 
		insertPayment(paymentDto, reqPaymentDto);

		//5. 결제 포인트 매핑 테이블 insert
		insertPaypoint(payPoint, paymentDto.getPayNo());
		
		//6. 결제 상품 매핑 테이블 insert
		insertPayProductTable(paymentDto, reqPaymentDto);

		return true;
	}

	private void insertPaypoint(LinkedList<PayPointDto> payPoint, long payNo) throws SQLException {
		for(int i = 0 ;  i < payPoint.size() ; i++) {
			payPoint.get(i).setPayNo(payNo);
			paymentMapper.insertPayPointTable(payPoint.get(i));
		}
	}

	@Transactional
	public int insertPayment(PaymentDto paymentDto, RequestPaymentDto reqPaymentDto) throws Exception{
		paymentDto.setPayment(reqPaymentDto.getTotalPrice(), reqPaymentDto.getUserMoney(), 'n');
		return paymentMapper.insertPayment(paymentDto);
	}

	@Transactional
	private void updateCouponTable(List<CouponDto> couponList, RequestPaymentDto reqPaymentDto,PaymentDto paymentDto) throws SQLException {
		long couponNo = 1;
		if(reqPaymentDto.getPayCoupon() == 0) return;

		int listSize = couponList.size();
		for(int i = 0 ; i<listSize; i++) {
			if(couponList.get(i).getType()==reqPaymentDto.getPayCoupon()) {
				couponNo=couponList.get(i).getCouponNo();
				paymentMapper.updateUsedCoupon(couponNo);
				break;
			}
		}

		paymentDto.setCouponNo(couponNo);
	}

	@Transactional
	private void updatePointTable(List<PointDto> pointList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto, LinkedList<PayPointDto> payPoint) throws SQLException {
		int requestPoint = reqPaymentDto.getPayPoint();

		if(requestPoint <= 0) return;

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
				paymentMapper.updatePointTable(updatePoint);
				payPoint.push(registPoint);
				requestPoint -= usablePoint;
			}
			else {
				updatePoint.setBalancePoint(usablePoint - requestPoint);
				registPoint.setUsePoint(requestPoint);
				paymentMapper.updatePointTable(updatePoint);
				payPoint.push(registPoint);
				requestPoint = 0;
				break;
			}

		}
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
	private void updatePaymentTable(PaymentDto paymentDto, RequestPaymentDto reqPaymentDto) throws SQLException {
		paymentDto.setPayment(reqPaymentDto.getTotalPrice(), reqPaymentDto.getUserMoney(), 'n');
		paymentMapper.updatePaymentTable(paymentDto);
	}

	@Transactional
	private void insertPayProductTable(PaymentDto paymentDto,RequestPaymentDto reqPaymentDto) throws SQLException {
		PayProductDto payProductDto = PayProductDto.builder()
				.productNo(reqPaymentDto.getProductNo())
				.payNo(paymentDto.getPayNo())
				.productCnt(reqPaymentDto.getProductCnt())
				.build();
		
		paymentMapper.insertPayProductTable(payProductDto);
	}


	public RequestPaymentDto getAutoPayment(RequestPaymentDto orderDto) throws Exception {
		orderDto.setUserMoney(orderDto.getTotalPrice());

		applyCoupon(orderDto);

		applyPoint(orderDto);

		if(orderDto.getUserMoney() != 0 ) {
			orderDto = applySavemoney(orderDto);
		}
		if(orderDto.getUserMoney()<0) {
			throw new UsermoneyException();
		}
		return orderDto;
	}

	private void applyCoupon(RequestPaymentDto orderDto) throws Exception {
		int userMoney = orderDto.getUserMoney();
		if(orderDto.getTotalPrice() < Coupon.PERCENT5.getPrice()) return;

		List<CouponDto> couponList = getAutoCoupon(orderDto);

		int listSize = couponList.size();
		if (listSize < 0 ) return;

		int coupon = 0;

		coupon = couponList.get(0).getType();

		orderDto.setPayCoupon(coupon);
		userMoney = userMoney - userMoney * coupon /100; 
		orderDto.setUserMoney(userMoney);
	}


	private List<CouponDto> getAutoCoupon(RequestPaymentDto orderDto) {
		return paymentMapper.getAutoCoupon(orderDto);
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

	private int userEnablePoint(long userNo) throws SQLException {
		return paymentMapper.userEnablePoint(userNo); 
	}

}
