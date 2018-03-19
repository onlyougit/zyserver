package com.zyserver.frontservice.service.impl;

import com.zyserver.entity.NetpayFlow;
import com.zyserver.frontservice.pojo.Payment;
import com.zyserver.frontservice.service.IPaymentService;
import com.zyserver.repository.FundDetailRepository;
import com.zyserver.repository.FundRepository;
import com.zyserver.repository.NetpayFlowRepository;
import com.zyserver.repository.RechargeRepository;
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

	@Value("${netpay.notifyUrl}")
	private String notifyUrl;
	@Value("${netpay.returnUrl}")
	private String returnUrl;
	public static final Logger log = LoggerFactory.getLogger(PaymentService.class);
	public static final String MERCHANT_ID = "100000000002370";
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
		netpayFlow.setOrderId(payment.getOrderNo());
		netpayFlow.setCreateTime(new Date());
		netpayFlow.setAmount(payment.getTotalFee().toString());
		netpayFlowRepository.save(netpayFlow);
		return payment;
	}
	public Payment createPaymentInfo(BigDecimal amount){
		Payment payment = new Payment();
		payment.setBody("bonus");
		payment.setCharset("UTF-8");
		payment.setDefaultbank("ICBC");
		payment.setIsApp("web");
		payment.setMerchantId(MERCHANT_ID);
		payment.setNotifyUrl(returnUrl);
		payment.setOrderNo(getCurrentDateTimeStr());
		payment.setPaymentType("1");
		payment.setPaymethod("directPay");
		payment.setReturnUrl(returnUrl);
		payment.setService("online_pay");
		payment.setSignType("SHA");
		payment.setTitle("11111");
		payment.setTotalFee(amount);
		return payment;
	}
	public String getCurrentDateTimeStr(){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
}
