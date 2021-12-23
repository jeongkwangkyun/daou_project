package com.daou.project.Enum;

import lombok.Getter;

@Getter
public enum Respond {
	SUCCESS("success"),
	FAIL("fail");
	
	private final String type;
	Respond(String type){
		this.type = type;
	}
}
