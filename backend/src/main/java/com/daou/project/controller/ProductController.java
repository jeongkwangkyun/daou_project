package com.daou.project.controller;

import java.util.List;

<<<<<<< HEAD
import javax.validation.constraints.NotNull;

=======
>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daou.project.model.ProductDto;
import com.daou.project.model.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts() throws Exception{
		return new ResponseEntity<List<ProductDto>>(productService.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/{productNo}")
	public ResponseEntity<ProductDto> getProduct(@PathVariable("productNo")  @NotNull long productNo) throws Exception{
		return new ResponseEntity<ProductDto>(productService.getProduct(productNo), HttpStatus.OK);
	}
}
