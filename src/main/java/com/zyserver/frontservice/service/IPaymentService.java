package com.zyserver.frontservice.service;

import com.zyserver.frontservice.pojo.Payment;
import com.zyserver.frontservice.pojo.PaymentResponse;

import java.math.BigDecimal;


public interface IPaymentService {

	Payment placeOrder(String customerId, BigDecimal amount,String bankCard);

    boolean verification(PaymentResponse paymentResponse);
}