package com.daou.project.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(
		basePackages = "com.daou.project.model.mapper"
)
public class DatabaseConfig {

}
