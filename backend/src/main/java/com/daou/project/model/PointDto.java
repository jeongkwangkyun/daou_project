package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointDto {
	private int pointNo;
	private int userNo;
	private int savePoint;
	private int balancePoint;
	private String expirationDate;
	private char expirationYn;
}
