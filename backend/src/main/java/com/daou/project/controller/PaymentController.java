package com.daou.project.controller;

<<<<<<< HEAD

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

=======
>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RestController;

import com.daou.project.Enum.Respond;
=======
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
import com.daou.project.model.PaymentDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
<<<<<<< HEAD
=======
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
	
	private final PaymentService paymentService;
	
	@GetMapping("/{userNo}")
<<<<<<< HEAD
	public ResponseEntity<UserDataDto> listUserData(@PathVariable("userNo") @NotNull long userNo) throws Exception{
=======
	public ResponseEntity<UserDataDto> listUserData(@PathVariable("userNo") long userNo) throws Exception{
>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
		return new ResponseEntity<UserDataDto>(paymentService.listUserData(userNo),HttpStatus.OK);
	}
	
	@PostMapping("/direct")
<<<<<<< HEAD
	public ResponseEntity<String> registerPayment(@RequestBody @Valid RequestPaymentDto reqPaymentDto) throws Exception{
		if(paymentService.registerPayment(reqPaymentDto)) {
			return new ResponseEntity<String>(Respond.SUCCESS.getType(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(Respond.FAIL.getType(), HttpStatus.NO_CONTENT);
=======
	public ResponseEntity<String> registerPayment(@RequestBody RequestPaymentDto reqPaymentDto) throws Exception{
		if(paymentService.registerPayment(reqPaymentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
>>>>>>> f5c7aa6db126955edcab80e5b2478149b10b896a
	}
	
	@PostMapping("/auto")
	public ResponseEntity<RequestPaymentDto> getAutoPayment(@RequestBody RequestPaymentDto orderDto) throws Exception{
		return new ResponseEntity<RequestPaymentDto>(paymentService.getAutoPayment(orderDto), HttpStatus.OK);
	}
}
