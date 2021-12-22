package com.daou.project.Enum;

import lombok.Getter;

@Getter
public enum Coupon {
	
	PERCENT20(20,30000),
	PERCENT10(10,20000),
	PERCENT5(5,10000);
	
	private final int discountPercent;
	private final int price;
	
	Coupon(int discountPercent, int price) {
		this.discountPercent = discountPercent;
		this.price = price;
	}
}
