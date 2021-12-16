package com.daou.project.model.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daou.project.model.CouponDto;
import com.daou.project.model.PayPointDto;
import com.daou.project.model.PaymentDto;
import com.daou.project.model.PointDto;
import com.daou.project.model.RequestPaymentDto;
import com.daou.project.model.SaveMoneyDto;
import com.daou.project.model.UserDataDto;
import com.daou.project.model.mapper.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final SqlSession sqlSession;
	
	private final PaymentMapper paymentMapper;
	
	@Override
	public UserDataDto listUserData(String userId) throws Exception {
		// TODO Auto-generated method stub
		UserDataDto userDataDto = new UserDataDto(getPoint(userId), getCoupon(userId), getSaveMoney(userId));
		return userDataDto;
	}


	public List<PointDto> getPoint(String userId) throws Exception{
		// TODO Auto-generated method stub
		return paymentMapper.getPoint(userId);
	}


	@Override
	public List<CouponDto> getCoupon(String userId) throws Exception {
		// TODO Auto-generated method stub
		return paymentMapper.getCoupon(userId);
	}


	@Override
	public SaveMoneyDto getSaveMoney(String userId) throws Exception {
		// TODO Auto-generated method stub
		return  paymentMapper.getSaveMoney(userId);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception {
		// TODO Auto-generated method stub
		UserDataDto userDataDto = new UserDataDto(getPoint(reqPaymentDto.getUserId()), getCoupon(reqPaymentDto.getUserId()), getSaveMoney(reqPaymentDto.getUserId()));
		
		
		//1. 임시 Payment 테이블 등록하기 
		PaymentDto paymentDto=registerTempTable();
		//2. Coupon 테이블 최신화
		
		//3. Point 테이블, Payment_Point 최신화
		
		//4. savemoney 테이블 최신화
		
		//5. 테이블 최신화
		
		//결제해야할 남은포인트
//		int remainPoint = reqPaymentDto.getPayPoint();
//		Queue<PayPointDto> queue = new LinkedList<PayPointDto>();
//		
//		for(int i=0; i< userDataDto.getPointList().size() ; i++) {
//			if(remainPoint == 0 ) {
//				break;
//			}
//			else if( remainPoint <= userDataDto.getPointList().get(i).getBalancePoint()) {
//			}
//		}
//		
		return false;
	}


	public PaymentDto registerTempTable( ) throws Exception{
		// TODO Auto-generated method stub
		return sqlSession.getMapper(PaymentMapper.class).registerTempTable();
	}




}
