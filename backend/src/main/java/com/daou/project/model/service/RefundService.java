package com.daou.project.model.service;

import java.util.List;

import com.daou.project.model.RefundDto;

public interface RefundService {

	List<RefundDto> getAllRefunds(long userNo) throws Exception;

	boolean registerRefund(long payNo) throws Exception;

}
