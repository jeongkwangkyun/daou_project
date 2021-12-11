package com.daou.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daou.project.model.ProductDto;
import com.daou.project.model.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> listProduct() throws Exception{
		logger.info("listProduct 호출");
		return new ResponseEntity<List<ProductDto>>(productService.listProduct(), HttpStatus.OK);
	}
	
}
