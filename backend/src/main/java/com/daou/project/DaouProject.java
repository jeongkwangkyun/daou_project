package com.daou.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.daou.project.model.mapper")
public class DaouProject {

	public static void main(String[] args) {
		SpringApplication.run(DaouProject.class, args);
	}

}
