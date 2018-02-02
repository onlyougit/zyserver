package com.cwp.frontservice.controler;

import com.cwp.common.Constant;
import com.cwp.frontservice.pojo.NetPayResponse;
import com.cwp.frontservice.pojo.PaymentInfo;
import com.cwp.frontservice.service.INetPayService;
import com.cwp.util.common.MD5Util;

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
@RequestMapping("/serverInterface/netpay")
public class NetPayController {
	public static final Logger log = LoggerFactory.getLogger(NetPayController.class);
	@Autowired
	private INetPayService netPayService;
	
	//@NeedSignIn
	@RequestMapping(value = "/placeOrder")
	public ModelAndView placeOrder(String customerId, BigDecimal amount) {
		if(StringUtils.isEmpty(customerId) || StringUtils.isEmpty(amount)){
			return null;
		}
		// 获取IP
		PaymentInfo paymentInfo = netPayService.placeOrder(customerId, amount);
		Map<String, Object> map = new HashMap<>();
		map.put("inputCharset", paymentInfo.getInputCharset());
		map.put("pickupUrl", paymentInfo.getPickupUrl());
		map.put("receiveUrl", paymentInfo.getReceiveUrl());
		map.put("version", paymentInfo.getVersion());
		map.put("language", paymentInfo.getLanguage());
		map.put("signType", paymentInfo.getSignType());
		map.put("merchantId", paymentInfo.getMerchantId());
		map.put("orderNo", paymentInfo.getOrderNo());
		map.put("orderAmount", paymentInfo.getOrderAmount());
		map.put("orderCurrency", paymentInfo.getOrderCurrency());
		map.put("orderDatetime", paymentInfo.getOrderDatetime());
		map.put("productName", paymentInfo.getProductName());
		map.put("ext2", paymentInfo.getExt2());
		map.put("payType", paymentInfo.getPayType());
		map.put("signMsg", paymentInfo.getSignMsg());
		map.put("reqUrl", Constant.PAY_URL);
		return new ModelAndView("public/gotoPlainPay", map);
	}

	@RequestMapping(value = "/verification")
	@ResponseBody
	public String verification(HttpServletRequest request){
		String merchantId = request.getParameter("merchantId");
		String version = request.getParameter("version");
		String language = request.getParameter("language");
		String signType = request.getParameter("signType");
		String payType = request.getParameter("payType");
		String issuerId = request.getParameter("issuerId");
		String mchtOrderId = request.getParameter("mchtOrderId");
		String orderNo = request.getParameter("orderNo");
		String merTranTime = request.getParameter("merTranTime");
		String orderAmount = request.getParameter("orderAmount");
		String payDatetime = request.getParameter("payDatetime");
		String ext1 = request.getParameter("ext1");
		String ext2 = request.getParameter("ext2");
		String payResult = request.getParameter("payResult");
		String signMsg = request.getParameter("signMsg");
		NetPayResponse netPayResponse = new NetPayResponse();
		netPayResponse.setMerchantId(merchantId);
		netPayResponse.setVersion(version);
		netPayResponse.setLanguage(language);
		netPayResponse.setSignType(signType);
		netPayResponse.setPayType(payType);
		netPayResponse.setIssuerId(issuerId);
		netPayResponse.setMchtOrderId(mchtOrderId);
		netPayResponse.setOrderNo(orderNo);
		netPayResponse.setMerTranTime(merTranTime);
		netPayResponse.setOrderAmount(orderAmount);
		netPayResponse.setPayDatetime(payDatetime);
		netPayResponse.setExt1(ext1);
		netPayResponse.setExt2(ext2);
		netPayResponse.setPayResult(payResult);
		netPayResponse.setSignMsg(signMsg);
		log.info("成功返回数据："+netPayResponse);
		boolean result = netPayService.verification(netPayResponse);
		if(result){
			return "success";
		}else{
			return "failed";
		}
	}
}