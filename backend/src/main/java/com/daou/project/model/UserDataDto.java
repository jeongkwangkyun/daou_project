package com.daou.project.model;

import java.util.List;

import lombok.Getter;

@Getter
public class UserDataDto {
	private List<PointDto> pointList;
	private List<CouponDto> couponList;
	private SaveMoneyDto savemoneyList;
	public UserDataDto(List<PointDto> pointList, List<CouponDto> couponList, SaveMoneyDto savemoneyList) {
		super();
		this.pointList = pointList;
		this.couponList = couponList;
		this.savemoneyList = savemoneyList;
	}
	
}
