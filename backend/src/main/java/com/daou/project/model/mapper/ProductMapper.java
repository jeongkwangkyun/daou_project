package com.daou.project.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daou.project.model.ProductDto;

@Mapper
public interface ProductMapper {
	public List<ProductDto> listProduct() throws SQLException;

}
