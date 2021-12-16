package com.daou.project.model.service;

import java.util.List;

import com.daou.project.model.ProductDto;

public interface ProductService {
	public List<ProductDto> listProduct() throws Exception;

	public ProductDto getProduct(int productNo) throws Exception;
	
}
