package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestPaymentDto {
	//결제페이지에서 요청 dto
	private long userNo;
	private long productNo;
	private int productCnt;
	private int totalPrice;
	private int payCoupon;
	private int paySaveMoney;
	private int payPoint;
	private int userMoney;

}
