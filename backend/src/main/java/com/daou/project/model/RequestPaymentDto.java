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
	@Valid
	@NotNull
	@Positive
	private long userNo;
	
	@Valid
	@NotNull
	private long productNo;
	
	@Valid
	@Min(value=1)
	private int productCnt;
	
	@Valid
	@Positive
	private int totalPrice;
	
	@Valid
	@PositiveOrZero
	private int payCoupon;
	
	@Valid
	@PositiveOrZero
	private int paySaveMoney;
	
	@Valid
	@PositiveOrZero
	private int payPoint;
	
	@Valid
	@PositiveOrZero
	private int userMoney;

}
