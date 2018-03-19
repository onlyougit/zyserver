package com.zyserver.frontservice.controler;

import com.zyserver.common.Constant;
import com.zyserver.frontservice.pojo.NetPayResponse;
import com.zyserver.frontservice.pojo.Payment;
import com.zyserver.frontservice.pojo.PaymentInfo;
import com.zyserver.frontservice.service.INetPayService;
import com.zyserver.frontservice.service.IPaymentService;
import com.zyserver.util.payment.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/serverInterface/payment")
public class PaymentController {
	public static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	public static final String SECRET_KEY = "74jgwpgjjwuuku1oy6xi6838r146t3gl7n6s9xpwr705mrcwdjia5i0q3tgkv7w9";

	@Autowired
	private IPaymentService paymentService;
	
	//@NeedSignIn
	@RequestMapping(value = "/placeOrder")
	public ModelAndView placeOrder(String customerId, BigDecimal amount) {
		if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(amount)){
			return null;
		}
		Payment payment = paymentService.placeOrder(customerId, amount);
		Map<String, String> map = new HashMap<>();
		map.put("totalFee", payment.getTotalFee().toString());
		map.put("body", payment.getBody());
		map.put("charset", payment.getCharset());
		map.put("defaultbank", payment.getDefaultbank());
		map.put("isApp", payment.getIsApp());
		map.put("merchantId", payment.getMerchantId());
		map.put("notifyUrl", payment.getNotifyUrl());
		map.put("orderNo", payment.getOrderNo());
		map.put("paymentType", payment.getPaymentType());
		map.put("paymethod", payment.getPaymethod());
		map.put("returnUrl", payment.getReturnUrl());
		map.put("service", payment.getService());
		map.put("title", payment.getTitle());
		String sign = SignUtil.GetSHAstr(map,SECRET_KEY);
		map.put("signType", payment.getSignType());
		map.put("sign", sign);
		map.put("reqUrl", Constant.PAYMENT_URL+payment.getMerchantId()+"-"+payment.getOrderNo());
		return new ModelAndView("public/gotoPayment", map);
	}

}