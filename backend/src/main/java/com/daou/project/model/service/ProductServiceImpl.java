package com.daou.project.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daou.project.model.ProductDto;
import com.daou.project.model.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper productMapper;
	
	@Override
	public List<ProductDto> getAllProducts() throws Exception {
		return productMapper.getAllProducts();
	}

	@Override
	public ProductDto getProduct(long productNo) throws Exception {
		return productMapper.getProduct(productNo);
	}

}
