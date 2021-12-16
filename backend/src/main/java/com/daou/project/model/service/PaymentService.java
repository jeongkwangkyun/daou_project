package com.daou.project.model.service;

import java.util.List;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;


public interface PaymentService {
	public UserDataDto listUserData(String userId) throws Exception;
	public List<CouponDto> getCoupon(String userId) throws Exception;
	public SaveMoneyDto getSaveMoney(String userId) throws Exception;
	public List<PointDto> getPoint(String userId) throws Exception;
	
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception;
	public PaymentDto registerTempTable( ) throws Exception;
}
