package com.daou.project.model.service;

import java.util.List;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;


public interface PaymentService {
	public UserDataDto listUserData(long userNo) throws Exception;
	public List<CouponDto> getCoupon(long userNo) throws Exception;
	public SaveMoneyDto getSaveMoney(long userNo) throws Exception;
	public List<PointDto> getPoint(long userNo) throws Exception;
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception;
	public int insertPayment(PaymentDto paymentDto,  RequestPaymentDto reqPaymentDto) throws Exception;
	public List<CouponDto> getCouponNoDuplicated(long userNo) throws Exception;
	public RequestPaymentDto getAutoPayment(RequestPaymentDto orderDto) throws Exception;
}
