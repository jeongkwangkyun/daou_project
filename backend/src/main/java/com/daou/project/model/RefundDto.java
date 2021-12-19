package com.daou.project.model;

import lombok.Getter;

@Getter
public class RefundDto {
	private long payNo;
	private String productName;
	private int productCnt;
	private int totalPrice;
	private int couponType;
	private int usePoint;
	private int useSavemoney;
	private int userMoney;
	private String payDate;
	private char refundYn;
}
