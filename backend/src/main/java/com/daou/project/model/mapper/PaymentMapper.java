package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PayPointDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.ProductDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;

@Mapper
public interface PaymentMapper {
	public List<CouponDto> getCoupon(int userNo) throws SQLException;
	public SaveMoneyDto getSaveMoney(int userNo) throws SQLException;
	public List<PointDto> getPoint(int userNo) throws SQLException;
	
	public int registerTempTable(PaymentDto paymentDto) throws SQLException;
	public List<CouponDto> getCouponNoDuplicated(int userNo) throws SQLException;
	public void updateCoupon(int couponNo) throws SQLException;
	public void updatePointTable(PointDto updatePoint) throws SQLException;
	public void insertPayPointTable(PayPointDto registPoint) throws SQLException;
	public void updateSavemoney(SaveMoneyDto savemoneyList) throws SQLException;
	public void updatePaymentTable(PaymentDto paymentDto) throws SQLException;
}
