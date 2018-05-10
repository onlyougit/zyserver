package com.zyserver.frontservice.controler;

import com.zyserver.frontservice.pojo.Payment;
import com.zyserver.frontservice.pojo.PaymentResponse;
import com.zyserver.frontservice.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/serverInterface/payment")
public class PaymentController {
	public static final Logger log = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	private IPaymentService paymentService;
	
	//@NeedSignIn
	@RequestMapping(value = "/placeOrder")
	public ModelAndView placeOrder(String customerId, BigDecimal amount) {
		if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(amount)){
			return null;
		}
		Payment payment = paymentService.placeOrder(customerId, amount);
		Map<String, Object> map = new HashMap<>();
		map.put("version", payment.getVersion());
		map.put("merchantId", payment.getMerchantId());
		map.put("merchantUrl", payment.getMerchantUrl());
		map.put("responseMode", payment.getResponseMode());
		map.put("orderId", payment.getOrderId());
		map.put("currencyType", payment.getCurrencyType());
		map.put("amount", payment.getAmount());
		map.put("assuredPay", payment.getAssuredPay());
		map.put("time", payment.getTime());
		map.put("remark", payment.getRemark());
		map.put("mac", payment.getMac());
		map.put("merchantKey", payment.getMerchantKey());
		map.put("reqUrl", "https://www.unspay.com/unspay/page/linkbank/payRequest.do");
		return new ModelAndView("public/gotoPayment", map);
	}

	@RequestMapping(value = "/responseData")
	@ResponseBody
	public String responseData(PaymentResponse paymentResponse){
		log.info(paymentResponse.toString());
		boolean result = paymentService.verification(paymentResponse);
		if(result){
			return "success";
		}else{
			return "failed";
		}
	}
}