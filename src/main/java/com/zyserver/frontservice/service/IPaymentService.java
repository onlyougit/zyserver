package com.zyserver.frontservice.service;

import com.zyserver.frontservice.pojo.Payment;
import java.math.BigDecimal;


public interface IPaymentService {

	Payment placeOrder(String customerId, BigDecimal amount);
}