package com.daou.project.Enum;

import lombok.Getter;

@Getter
public enum CouponEnum {
	
	COUPON20(20,30000),
	COUPON10(10,20000),
	COUPON5(5,10000);
	
	private final int discountPercent;
	private final int price;
	
	CouponEnum(int discountPercent, int price) {
		this.discountPercent = discountPercent;
		this.price = price;
	}
}
