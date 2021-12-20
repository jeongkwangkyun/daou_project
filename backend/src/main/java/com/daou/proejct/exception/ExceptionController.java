package com.daou.proejct.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "com.daou.project.controller")
public class ExceptionController extends ResponseEntityExceptionHandler{

	private static final String WRONG = "wrong";
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> paymentValidationExceptions(MethodArgumentNotValidException ex){
	    logger.info("paymentValidationExceptions class 출력"); 
		return new ResponseEntity<String>(WRONG, HttpStatus.BAD_REQUEST);
	}
}
