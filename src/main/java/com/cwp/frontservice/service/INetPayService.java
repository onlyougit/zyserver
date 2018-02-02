package com.cwp.frontservice.service;

import com.cwp.frontservice.pojo.NetPayResponse;
import com.cwp.frontservice.pojo.PaymentInfo;

import java.math.BigDecimal;



public interface INetPayService {

	PaymentInfo placeOrder(String customerId, BigDecimal amount);

	boolean verification(NetPayResponse netPayResponse);
}