package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
	// Payment 테이블에 담을 데이터

	private long payNo;
	private long savemoneyNo;
	private int useSavemoney;
	private long userNo;
	private long couponNo;
	private int totalPrice;
	private int userMoney;
	private String payDate;
	private char refundYn;
	public PaymentDto() {};

	public PaymentDto(long payNo, long savemoneyNo, long userNo, long couponNo, int totalPrice, int userMoney,
			String payDate, char refundYn) {
		super();
		this.payNo = payNo;
		this.savemoneyNo = savemoneyNo;
		this.userNo = userNo;
		this.couponNo = couponNo;
		this.totalPrice = totalPrice;
		this.userMoney = userMoney;
		this.payDate = payDate;
		this.refundYn = refundYn;
	}

	public void setPayment(int totalPrice, int userMoney, char refundYn) {
		this.totalPrice = totalPrice;
		this.userMoney = userMoney;
		this.refundYn = refundYn;
	}

	
}
