package com.daou.project.controller;

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

import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private final PaymentService paymentService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDataDto> listUserData(@PathVariable("userId")String userId) throws Exception{
		return new ResponseEntity<UserDataDto>(paymentService.listUserData(userId),HttpStatus.OK);
	}
	
	@PostMapping("/direct")
	public ResponseEntity<String> registerPayment(@RequestBody RequestPaymentDto reqPaymentDto) throws Exception{

		logger.info("registPayment - 호출");
		if(paymentService.registerPayment(reqPaymentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
