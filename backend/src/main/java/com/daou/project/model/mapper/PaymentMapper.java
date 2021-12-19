package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PayPointDto;
import com.daou.project.model.PayProductDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.SaveMoneyDto;

@Mapper
public interface PaymentMapper {
	public List<CouponDto> getCoupon(long userNo) throws SQLException;
	public SaveMoneyDto getSaveMoney(long userNo) throws SQLException;
	public List<PointDto> getPoint(long userNo) throws SQLException;
	
	public int registerTempTable(PaymentDto paymentDto) throws SQLException;
	public List<CouponDto> getCouponNoDuplicated(long userNo) throws SQLException;
	public void updateCoupon(long couponNo) throws SQLException;
	public void updatePointTable(PointDto updatePoint) throws SQLException;
	public void insertPayPointTable(PayPointDto registPoint) throws SQLException;
	public void updateSavemoney(SaveMoneyDto savemoneyList) throws SQLException;
	public void updatePaymentTable(PaymentDto paymentDto) throws SQLException;
	public void insertPayProducTable(PayProductDto payProductDto) throws SQLException;
	public int sumPoint(long userNo) throws SQLException;
}
