package com.daou.project.Enum;

import lombok.Getter;

@Getter
public enum CouponEnum {
	
	COUPON_A(20,30000),
	COUPON_B(10,20000),
	COUPON_C(5,10000);
	
	private final int type;
	private final int price;
	
	CouponEnum(int type, int price) {
		this.type = type;
		this.price = price;
	}
}
