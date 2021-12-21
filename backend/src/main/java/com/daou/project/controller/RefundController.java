package com.daou.project.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daou.project.model.RefundDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.service.RefundService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refund")
public class RefundController {
	private static final Logger logger = LoggerFactory.getLogger(RefundController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final RefundService refundService;
	
	
	@GetMapping("/{userNo}")
	public ResponseEntity<List<RefundDto>> getAllRefunds(@PathVariable("userNo") @NotNull long userNo) throws Exception{
		return new ResponseEntity<List<RefundDto>>(refundService.getAllRefunds(userNo), HttpStatus.OK);
	}
	
	@PostMapping("/{payNo}")
	public ResponseEntity<String> registerRefund(@PathVariable("payNo") @NotNull long payNo) throws Exception{
		if(refundService.registerRefund(payNo)) {			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		
	}
}
