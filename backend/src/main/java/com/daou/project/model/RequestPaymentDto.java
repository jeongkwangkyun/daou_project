package com.daou.project.model;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RequestPaymentDto {
	//결제페이지에서 요청 dto
	@NotNull
	@Positive
	private long userNo;
	
	@NotNull
	private long productNo;
	
	@Min(value=1)
	private int productCnt;
	
	@Positive
	private int totalPrice;
	
	@PositiveOrZero
	private int payCoupon;

	@PositiveOrZero
	private int paySaveMoney;
	
	@PositiveOrZero
	private int payPoint;
	
	@PositiveOrZero
	private int userMoney;

}
