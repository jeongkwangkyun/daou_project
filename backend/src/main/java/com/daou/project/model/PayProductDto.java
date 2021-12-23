package com.daou.project.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PayProductDto {
	private long productNo;
	private long payNo;
	private int productCnt;
	
	@Builder
	public PayProductDto(long productNo, long payNo, int productCnt) {
		this.productNo = productNo;
		this.payNo = payNo;
		this.productCnt = productCnt;
	}
}
