package com.daou.project.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daou.project.model.PayPointDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.RefundDoneDto;
import com.daou.project.model.RefundDto;
import com.daou.project.model.mapper.RefundMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
	
	private final RefundMapper refundMapper;
	
	public List<RefundDto> getAllRefunds(long userNo) throws Exception {
		
		return refundMapper.getAllRefunds(userNo);
	}

	@Override
	@Transactional
	public boolean registerRefund(long payNo) throws Exception {
		
		RefundDoneDto refundDto = new RefundDoneDto();
		
		PaymentDto paymentDto = getPaymentData(payNo);
		
		// 1. 적립금
		updateSavemoneyTable(paymentDto);
		
		// 2. 포인트
		updatePointTable(paymentDto.getPayNo());
		
		// 3. 쿠폰
		updateCouponTable(paymentDto.getCouponNo());
		
		//4. payment
		updatePaymentTable(payNo);
		
		//5. refund
		refundDto.setPayNo(payNo);
		refundDto.setUserNo(paymentDto.getUserNo());
		insertRefundTable(refundDto);
		return true;
	}

	private void insertRefundTable(RefundDoneDto refundDto) throws SQLException {
		refundMapper.insertRefundTable(refundDto);
	}

	private void updatePaymentTable(long payNo) throws SQLException {
		refundMapper.updatePaymentTable(payNo);
	}

	private void updatePointTable(long payNo) throws SQLException {
		List<PayPointDto> pointList = refundMapper.getPoint(payNo);
		int listSize = pointList.size();
		for(int i = 0 ; i < listSize ; i ++) {
			refundMapper.updatePointTable(pointList.get(i));
		}
	}

	private void updateCouponTable(long couponNo) throws SQLException {
		refundMapper.updateCouponTable(couponNo);
	}

	private void updateSavemoneyTable(PaymentDto paymentDto) throws SQLException {
		refundMapper.updateSavemoneyTable(paymentDto);
	}

	private PaymentDto getPaymentData(long payNo) throws SQLException {
		return refundMapper.getPaymentData(payNo);
	}

}
