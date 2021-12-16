package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {
	// Payment 테이블에 담을 데이터
	private int payNo;
	private int saveMoneyNo;
	private int userNo;
	private int couponNo;
	private int totalPrice;
	private int userMoney;
	private String payDate;
	private char refundYn;
}
