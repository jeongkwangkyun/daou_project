package com.daou.proejct.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class ExceptionController{
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> paymentValidationExceptions(MethodArgumentNotValidException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.FAILED_DEPENDENCY);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<String> sqlValidException(SQLException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(SavemoneyException.class)
	public ResponseEntity<String> SavemoneyValidationExceptions(SavemoneyException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PointException.class)
	public ResponseEntity<String> PointValidationExceptions(PointException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CouponException.class)
	public ResponseEntity<String> CouponValidationExceptions(CouponException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UsermoneyException.class)
	public ResponseEntity<String> UsermoneyValidationExceptions(CouponException exception){
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
