package com.zyserver.frontservice.service.impl;

import com.zyserver.entity.NetpayFlow;
import com.zyserver.frontservice.pojo.Payment;
import com.zyserver.frontservice.pojo.PaymentResponse;
import com.zyserver.frontservice.service.IPaymentService;
import com.zyserver.repository.FundDetailRepository;
import com.zyserver.repository.FundRepository;
import com.zyserver.repository.NetpayFlowRepository;
import com.zyserver.repository.RechargeRepository;
import com.zyserver.util.common.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PaymentService implements IPaymentService {

	public static final Logger log = LoggerFactory.getLogger(PaymentService.class);
	public static final String MERCHANT_ID = "1120180419172457001";
	public static final String MERCHANT_KEY = "123456abc";
	public static final String MERCHANT_URL = "http://payfor.chundongh.cn/serverInterface/payment/responseData";
	public static final Integer RESPONSE_MODE = 1;
	public static final String CURRENCY_TYPE = "CNY";
	public static final String REMARK = "remark";
	@Autowired
	private NetpayFlowRepository netpayFlowRepository;
	@Autowired
	private RechargeRepository rechargeRepository;
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	private FundDetailRepository fundDetailRepository;
	
	
	@Override
	public Payment placeOrder(String customerId, BigDecimal amount) {
		log.info("支付接口请求开始>>>>>>>>>>>>>>>>>>>");
		Payment payment = createPaymentInfo(amount);
		NetpayFlow netpayFlow = new NetpayFlow();
		netpayFlow.setCustomerId(Integer.parseInt(customerId));
		netpayFlow.setMerchantId(MERCHANT_ID);
		netpayFlow.setOrderId(payment.getOrderId());
		netpayFlow.setCreateTime(new Date());
		netpayFlow.setAmount(payment.getAmount().toString());
		netpayFlowRepository.save(netpayFlow);
		return payment;
	}

	@Override
	public boolean verification(PaymentResponse paymentResponse) {
		return false;
	}

	public Payment createPaymentInfo(BigDecimal amount){
		Payment payment = new Payment();
		payment.setVersion("3.0.0");
		payment.setMerchantId(MERCHANT_ID);
		payment.setMerchantUrl(MERCHANT_URL);
		payment.setResponseMode(RESPONSE_MODE);
		payment.setMerchantId(MERCHANT_ID);
		String orderId = getCurrentDateTimeStr();
		payment.setOrderId(orderId);
		payment.setCurrencyType(CURRENCY_TYPE);
		payment.setAmount(amount);
		payment.setAssuredPay("");
		payment.setTime(orderId);
		payment.setRemark(REMARK);
		payment.setMerchantKey(MERCHANT_KEY);
		payment.setMac(getMac(MERCHANT_ID,MERCHANT_URL,RESPONSE_MODE,orderId,CURRENCY_TYPE,amount,"",orderId,REMARK,MERCHANT_KEY));
		return payment;
	}

	public String getMac(String merchantId, String merchantUrl, Integer responseMode, String orderId, String currencyType, BigDecimal amount,
						  String assuredPay, String orderId1, String remark, String merchantKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("merchantId=");
		sb.append(merchantId);
		sb.append("&merchantUrl=");
		sb.append(merchantUrl);
		sb.append("&responseMode=");
		sb.append(responseMode);
		sb.append("&orderId=");
		sb.append(orderId);
		sb.append("&currencyType=");
		sb.append(currencyType);
		sb.append("&amount=");
		sb.append(amount);
		sb.append("&assuredPay=");
		sb.append(assuredPay);
		sb.append("&time=");
		sb.append(orderId1);
		sb.append("&remark=");
		sb.append(remark);
		sb.append("&merchantKey=");
		sb.append(merchantKey);
		String mac = MD5Util.HEXAndMd5(sb.toString());
		return mac;
	}
	public String getSign(String merchantId, Integer responseMode, String orderId, String currencyType, BigDecimal amount,
						  String returnCode, String returnMessage, String merchantKey) {
		StringBuilder sb = new StringBuilder();
		sb.append("merchantId=");
		sb.append(merchantId);
		sb.append("&responseMode=");
		sb.append(responseMode);
		sb.append("&orderId=");
		sb.append(orderId);
		sb.append("&currencyType=");
		sb.append(currencyType);
		sb.append("&amount=");
		sb.append(amount);
		sb.append("&returnCode=");
		sb.append(returnCode);
		sb.append("&returnMessage=");
		sb.append(returnMessage);
		sb.append("&merchantKey=");
		sb.append(merchantKey);
		String mac = MD5Util.HEXAndMd5(sb.toString());
		return mac;
	}

	public String getCurrentDateTimeStr(){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
}
