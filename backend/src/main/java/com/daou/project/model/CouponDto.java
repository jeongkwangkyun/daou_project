package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponDto {
	private int couponNo;
	private int userNo;
	private int type;
	private char couponYn;
}