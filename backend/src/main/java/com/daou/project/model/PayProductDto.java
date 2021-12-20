package com.daou.project.model;

import lombok.Getter;

@Getter
public class PayProductDto {
	private long productNo;
	private long payNo;
	private int productCnt;
	public PayProductDto(long productNo, long payNo, int productCnt) {
		super();
		this.productNo = productNo;
		this.payNo = payNo;
		this.productCnt = productCnt;
	}
}
