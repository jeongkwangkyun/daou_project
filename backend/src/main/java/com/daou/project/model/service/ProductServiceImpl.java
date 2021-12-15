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
	
	private final SqlSession sqlSession;
	
	@Override
	public List<ProductDto> listProduct() throws Exception {
		// TODO Auto-generated method stub
		
		return sqlSession.getMapper(ProductMapper.class).listProduct();
	}

}
