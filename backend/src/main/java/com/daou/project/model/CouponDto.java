package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CouponDto {
	private long couponNo;
	private long userNo;
	private int type;
	private char couponYn;
}
