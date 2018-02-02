package com.zyserver.frontservice.service;

import com.zyserver.frontservice.pojo.NetPayResponse;
import com.zyserver.frontservice.pojo.PaymentInfo;

import java.math.BigDecimal;



public interface INetPayService {

	PaymentInfo placeOrder(String customerId, BigDecimal amount);

	boolean verification(NetPayResponse netPayResponse);
}