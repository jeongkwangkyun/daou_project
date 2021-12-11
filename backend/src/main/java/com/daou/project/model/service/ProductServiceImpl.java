package com.daou.project.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daou.project.model.ProductDto;
import com.daou.project.model.mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public List<ProductDto> listProduct() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(sqlSession.getMapper(ProductMapper.class).listProduct());
		return sqlSession.getMapper(ProductMapper.class).listProduct();
	}

}
