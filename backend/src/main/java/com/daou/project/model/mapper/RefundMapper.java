package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.PayPointDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.RefundDoneDto;
import com.daou.project.model.RefundDto;

@Mapper
public interface RefundMapper {

	List<RefundDto> getAllRefunds(long userNo) throws SQLException;
	PaymentDto getPaymentData(long payNo) throws SQLException;
	void updateSavemoneyTable(PaymentDto paymentDto) throws SQLException;
	void updateCouponTable(long couponNo) throws SQLException;
	List<PayPointDto> getPoint(long payNo) throws SQLException;
	void updatePointTable(PayPointDto payPointDto) throws SQLException;
	void updatePaymentTable(long payNo) throws SQLException;
	void insertRefundTable(RefundDoneDto refundDto) throws SQLException;
}
