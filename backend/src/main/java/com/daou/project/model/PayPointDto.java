package com.daou.project.model;

import lombok.Builder;
import lombok.Setter;


@Setter
public class PayPointDto {
	// payment_point 테이블 넣어줄 dto
	private long pointNo;
	private long payNo;
	private long userNo;
	private int usePoint;

	
	@Builder
	public PayPointDto(long pointNo, long payNo, long userNo, int usePoint){
		this.pointNo = pointNo;
		this.payNo = payNo;
		this.userNo = userNo;
		this.usePoint = usePoint;
	}
}
