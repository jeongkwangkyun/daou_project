package com.daou.project.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScheduleMapper {

	void updateExpiration() throws SQLException;
	
}
