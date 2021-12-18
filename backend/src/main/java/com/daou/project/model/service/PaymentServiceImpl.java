package com.daou.project.model.service;

import java.sql.SQLException;
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

	private final PaymentMapper paymentMapper;

	@Override
	public UserDataDto listUserData(int userNo) throws Exception {
		// TODO Auto-generated method stub
		UserDataDto userDataDto = new UserDataDto(getPoint(userNo), getCoupon(userNo), getSaveMoney(userNo));
		return userDataDto;
	}


	public List<PointDto> getPoint(int userNo) throws Exception{
		// TODO Auto-generated method stub
		return paymentMapper.getPoint(userNo);
	}


	@Override
	public List<CouponDto> getCoupon(int userNo) throws Exception {
		// TODO Auto-generated method stub
		return paymentMapper.getCoupon(userNo);
	}

	@Override
	public List<CouponDto> getCouponNoDuplicated(int userNo) throws Exception {
		// TODO Auto-generated method stub
		return paymentMapper.getCouponNoDuplicated(userNo);
	}
	@Override
	public SaveMoneyDto getSaveMoney(int userNo) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		return  paymentMapper.getSaveMoney(userNo);
	}


	@Override
	@Transactional
	public boolean registerPayment(RequestPaymentDto reqPaymentDto) throws Exception {
		// TODO Auto-generated method stub
		UserDataDto userDataDto = new UserDataDto(getPoint(reqPaymentDto.getUserNo()), getCouponNoDuplicated(reqPaymentDto.getUserNo()), getSaveMoney(reqPaymentDto.getUserNo()));

		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setUserNo(reqPaymentDto.getUserNo());

		//1. 임시 Payment 테이블 등록하기 
		registerTempTable(paymentDto);

		//2. Coupon 테이블 최신화
		paymentDto = registerCouponTable(userDataDto.getCouponList(),reqPaymentDto, paymentDto);

		//3. Point 테이블, Payment_Point 최신화
		registerPointTable(userDataDto.getPointList(), reqPaymentDto, paymentDto);
		
		//4. savemoney 테이블 최신화
		paymentDto = registerSavemoneyTable(userDataDto.getSavemoneyList(), reqPaymentDto, paymentDto);
		
		//5. 테이블 최신화
		updatePaymentTable(paymentDto,reqPaymentDto);
		
		return true;
	}


	private void updatePaymentTable(PaymentDto paymentDto, RequestPaymentDto reqPaymentDto) throws SQLException {
		// TODO Auto-generated method stub
		paymentDto.setRefundYn('n');
		paymentDto.setTotalPrice(reqPaymentDto.getTotalPrice());
		paymentDto.setUserMoney(reqPaymentDto.getUserMoney());
		
		paymentMapper.updatePaymentTable(paymentDto);
	}


	private PaymentDto registerSavemoneyTable(SaveMoneyDto savemoneyList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		
		int savemoneyNo = 1;
		if (reqPaymentDto.getPaySaveMoney() !=0) {
			savemoneyNo = savemoneyList.getSavemoneyNo();
			paymentDto.setUseSavemoney(reqPaymentDto.getPaySaveMoney());
			
			savemoneyList.setSaveMoney(savemoneyList.getSaveMoney() - reqPaymentDto.getPaySaveMoney());
			paymentMapper.updateSavemoney(savemoneyList);
			
		}
		paymentDto.setSavemoneyNo(savemoneyNo);
		
		return paymentDto;
	}


	private void registerPointTable(List<PointDto> pointList, RequestPaymentDto reqPaymentDto,
			PaymentDto paymentDto) throws SQLException {
		int remainPoint = reqPaymentDto.getPayPoint();

		if(remainPoint>0) {
			for(int i=0 ; i<pointList.size(); i++) {
				PointDto updatePoint = pointList.get(i);
				PayPointDto registPoint = new PayPointDto();
				
				int usePoint = 0;

				usePoint = updatePoint.getBalancePoint();
				registPoint.setUsePoint(usePoint);
				registPoint.setPayNo(paymentDto.getPayNo());
				registPoint.setPointNo(updatePoint.getPointNo());
				registPoint.setUserNo(paymentDto.getUserNo());
				if(remainPoint>updatePoint.getBalancePoint()) {	
					updatePoint.setBalancePoint(0);
					paymentMapper.updatePointTable(updatePoint);
					paymentMapper.insertPayPointTable(registPoint);
					remainPoint -= usePoint;
				}
				else {
					updatePoint.setBalancePoint(usePoint - remainPoint);
					paymentMapper.updatePointTable(updatePoint);
					paymentMapper.insertPayPointTable(registPoint);
					break;
				}
			}
		}

	}

	private PaymentDto registerCouponTable(List<CouponDto> couponList, RequestPaymentDto reqPaymentDto,PaymentDto paymentDto) throws SQLException {
		// TODO Auto-generated method stub
		int couponNo = 1;
		if(reqPaymentDto.getPayCoupon()!=0) {
			for(int i = 0 ; i<couponList.size(); i++) {
				if(couponList.get(i).getType()==reqPaymentDto.getPayCoupon()) {
					couponNo=couponList.get(i).getCouponNo();
					paymentMapper.updateCoupon(couponNo);
					break;
				}
			}
		}
		paymentDto.setCouponNo(couponNo);
		return paymentDto;
	}


	public int registerTempTable(PaymentDto paymentDto) throws Exception{
		// TODO Auto-generated method stub
		return paymentMapper.registerTempTable(paymentDto);
	}

}
