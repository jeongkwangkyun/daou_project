package com.daou.project.controller;


import javax.validation.Valid;
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

import com.daou.project.model.PaymentDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final PaymentService paymentService;
	
	@GetMapping("/{userNo}")
	public ResponseEntity<UserDataDto> listUserData(@PathVariable("userNo") @NotNull long userNo) throws Exception{
		return new ResponseEntity<UserDataDto>(paymentService.listUserData(userNo),HttpStatus.OK);
	}
	
	@PostMapping("/direct")
	public ResponseEntity<String> registerPayment(@RequestBody @Valid RequestPaymentDto reqPaymentDto) throws Exception{
		if(paymentService.registerPayment(reqPaymentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/auto")
	public ResponseEntity<RequestPaymentDto> getAutoPayment(@RequestBody RequestPaymentDto orderDto) throws Exception{
		return new ResponseEntity<RequestPaymentDto>(paymentService.getAutoPayment(orderDto), HttpStatus.OK);
	}
}
