package com.daou.project.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final SqlSession sqlSession;
	
	
	@Override
	public UserDataDto listUserData(String userId) throws Exception {
		// TODO Auto-generated method stub
		List<CouponDto> couponList = getCoupon(userId);
		SaveMoneyDto saveMoneyList = getSaveMoney(userId);
		List<PointDto> pointList = getPoint(userId);
		UserDataDto userDataDto = new UserDataDto(pointList, couponList, saveMoneyList);
		return userDataDto;
	}


	public List<PointDto> getPoint(String userId) throws Exception{
		// TODO Auto-generated method stub
		return sqlSession.getMapper(PaymentMapper.class).getPoint(userId);
	}


	@Override
	public List<CouponDto> getCoupon(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(PaymentMapper.class).getCoupon(userId);
	}


	@Override
	public SaveMoneyDto getSaveMoney(String userId) throws Exception {
		// TODO Auto-generated method stub
		return  sqlSession.getMapper(PaymentMapper.class).getSaveMoney(userId);
	}

}
