package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.ProductDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;

@Mapper
public interface PaymentMapper {
	public List<UserDataDto> listUserData() throws SQLException;
	public List<CouponDto> getCoupon(String userId) throws SQLException;
	public SaveMoneyDto getSaveMoney(String userId) throws SQLException;
	public List<PointDto> getPoint(String userId) throws SQLException;
	
	public PaymentDto registerTempTable() throws SQLException;
}
