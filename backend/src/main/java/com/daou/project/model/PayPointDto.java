package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PayPointDto {
	// payment_point 테이블 넣어줄 dto
	private int pointNo;
	private int payNo;
	private int userNo;
	private int userPoint;
	public PayPointDto(int pointNo, int payNo, int userNo, int userPoint) {
		super();
		this.pointNo = pointNo;
		this.payNo = payNo;
		this.userNo = userNo;
		this.userPoint = userPoint;
	}
	
}
