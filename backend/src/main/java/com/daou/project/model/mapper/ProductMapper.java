package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.ProductDto;

@Mapper
public interface ProductMapper {
	public List<ProductDto> getAllProducts() throws SQLException;
	public ProductDto getProduct(long productNo) throws SQLException;

	public ProductDto getProduct(int productNo) throws SQLException;

}
