package com.daou.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
	private long productNo;
	private String productName;
	private int productPrice;
	private String productContent;
}
