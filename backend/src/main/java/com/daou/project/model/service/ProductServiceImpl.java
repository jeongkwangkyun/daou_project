package com.daou.project.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.daou.project.model.ProductDto;
import com.daou.project.model.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private final ProductMapper productMapper;
	
	@Override
	public List<ProductDto> listProduct() throws Exception {
		// TODO Auto-generated method stub
		return productMapper.listProduct();
	}

	@Override
	public ProductDto getProduct(int productNo) throws Exception {
		// TODO Auto-generated method stub

		return productMapper.getProduct(productNo);
	}

}
