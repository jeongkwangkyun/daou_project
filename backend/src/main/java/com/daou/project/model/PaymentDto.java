package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
	// Payment 테이블에 담을 데이터
	private int payNo;
	private int savemoneyNo;
	private int useSavemoney;
	private int userNo;
	private int couponNo;
	private int totalPrice;
	private int userMoney;
	private String payDate;
	private char refundYn;
	public PaymentDto() {};
	public PaymentDto(int payNo, int savemoneyNo, int userNo, int couponNo, int totalPrice, int userMoney,
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
	
}
