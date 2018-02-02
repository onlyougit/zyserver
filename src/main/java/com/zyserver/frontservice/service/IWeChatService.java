package com.zyserver.frontservice.service;

import java.math.BigDecimal;

import com.zyserver.common.ResponseJson;


public interface IWeChatService {

	ResponseJson<Object> placeOrder(Integer customerId,BigDecimal amount, String tradeType,
			String ipAddr) throws Exception;

	String orderCallback(String notityXml)throws Exception;
 
}