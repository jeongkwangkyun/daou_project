package com.daou.project.model.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.daou.project.model.mapper.ScheduleMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{ 
 
	private final ScheduleMapper scheduleMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	@Scheduled(cron="0 0 0 * * *")
    public void updateExpiration() throws SQLException {
        logger.info("DB Refund 테이블 expiration_yn 업데이트");
        scheduleMapper.updateExpiration();
    }
}