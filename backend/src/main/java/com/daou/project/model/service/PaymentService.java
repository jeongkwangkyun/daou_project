package com.daou.project.model.service;

import java.util.List;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;


public interface PaymentService {
	public UserDataDto listUserData(int userNo) throws Exception;
	public List<CouponDto> getCoupon(int userNo) throws Exception;
	public SaveMoneyDto getSaveMoney(int userNo) throws Exception;
	public List<PointDto> getPoint(int userNo) throws Exception;
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception;
	public int registerTempTable(PaymentDto paymentDto) throws Exception;
	public List<CouponDto> getCouponNoDuplicated(int userNo) throws Exception;
}
