package com.daou.proejct.exception;

public class SavemoneyException extends RuntimeException{
	public SavemoneyException() {
		super("savemoney");
	}
	
	public SavemoneyException(String message) {
		super(message);
	};
}
