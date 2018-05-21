package com.zyserver.frontservice.service.impl;

import com.zyserver.entity.Fund;
import com.zyserver.entity.FundDetail;
import com.zyserver.entity.NetpayFlow;
import com.zyserver.entity.Recharge;
import com.zyserver.enums.FlowWay;
import com.zyserver.enums.RechargeWay;
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
	public static final String MERCHANT_ID = "2120180428164017001";
	public static final String MERCHANT_KEY = "dy051226";
	public static final String MERCHANT_URL = "http://payfor.chundongh.cn/serverInterface/payment/responseData";
	public static final Integer RESPONSE_MODE = 2;
	public static final String CURRENCY_TYPE = "CNY";
	public static final String REMARK = "";
	public static final String ASSURED_PAY = "false";

	@Autowired
	private NetpayFlowRepository netpayFlowRepository;
	@Autowired
	private RechargeRepository rechargeRepository;
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	private FundDetailRepository fundDetailRepository;
	
	
	@Override
	public Payment placeOrder(Integer customerId, BigDecimal amount,String bankCard) {
		log.info("支付接口请求开始>>>>>>>>>>>>>>>>>>>");
		Payment payment = createPaymentInfo(amount);
		NetpayFlow netpayFlow = new NetpayFlow();
		netpayFlow.setCustomerId(customerId);
		netpayFlow.setMerchantId(MERCHANT_ID);
		netpayFlow.setOrderId(payment.getOrderId());
		netpayFlow.setCreateTime(new Date());
		netpayFlow.setAmount(payment.getAmount().toString());
		netpayFlowRepository.save(netpayFlow);
		return payment;
	}

	@Override
	public boolean verification(PaymentResponse paymentResponse) {
		//查询交易流水
		NetpayFlow netpayFlow = netpayFlowRepository.findByOrderId(paymentResponse.getOrderId());
		//判断金额
		if(new BigDecimal(netpayFlow.getAmount()).compareTo(paymentResponse.getAmount())!=0){
			log.info("支付金额不匹配");
			return false;
		}
		//判断mac
		String sign = getSign(paymentResponse.getMerchantId(),paymentResponse.getResponseMode(),paymentResponse.getOrderId(),
				paymentResponse.getCurrencyType(),paymentResponse.getAmount(),paymentResponse.getReturnCode(),paymentResponse.getReturnMessage(),MERCHANT_KEY);
		log.info("本地Mac："+sign+";三方Mac："+paymentResponse.getMac());
		if(!paymentResponse.getMac().equalsIgnoreCase(sign)){
			log.info("验证签名失败");
			return false;
		}
		BigDecimal amount = paymentResponse.getAmount().multiply(new BigDecimal("0.995")).setScale(2,BigDecimal.ROUND_HALF_UP);
		//更新交易流水
		netpayFlow.setModifyTime(new Date());
		netpayFlowRepository.save(netpayFlow);
		//往充值表加一条数据
		Recharge recharge = new Recharge();
		recharge.setCustomerId(netpayFlow.getCustomerId());
		recharge.setOrderId(paymentResponse.getOrderId());
		recharge.setRechargeAmount(amount);
		recharge.setRechargeTime(new Date());
		recharge.setRechargeWay(RechargeWay.ONLINEBANKING.getCode());
		recharge.setRemark(RechargeWay.ONLINEBANKING.getText());
		rechargeRepository.save(recharge);
		//更余额
		Fund fund = fundRepository.findByCustomerId(netpayFlow.getCustomerId());
		if(null != fund){
			fund.setBalance(fund.getBalance().add(amount).setScale(2,BigDecimal.ROUND_HALF_UP));
			fundRepository.save(fund);
			//添加资金明细
			FundDetail fundDetail = new FundDetail();
			fundDetail.setChangeAmount(amount);
			fundDetail.setChangeTime(new Date());
			fundDetail.setCustomerId(netpayFlow.getCustomerId());
			fundDetail.setChargeAmount(fund.getBalance());
			fundDetail.setFlowWay(FlowWay.INCOME.getCode());
			fundDetail.setRemark("银生网银充值");
			fundDetailRepository.save(fundDetail);
		}
		log.info("网银支付成功");
		return true;
	}

	public Payment createPaymentInfo(BigDecimal amount){
		Payment payment = new Payment();
		payment.setVersion("3.0.0");
		payment.setMerchantId(MERCHANT_ID);
		payment.setMerchantUrl(MERCHANT_URL);
		payment.setResponseMode(RESPONSE_MODE);
		String orderId = "zy"+getCurrentDateTimeStr();
		payment.setOrderId(orderId);
		payment.setCurrencyType(CURRENCY_TYPE);
		payment.setAmount(amount);
		payment.setAssuredPay(ASSURED_PAY);
		payment.setTime(getCurrentDateTimeStr());
		payment.setRemark(REMARK);
		payment.setMerchantKey(MERCHANT_KEY);
		payment.setMac(getMac(MERCHANT_ID,MERCHANT_URL,RESPONSE_MODE,orderId,CURRENCY_TYPE,amount,ASSURED_PAY,getCurrentDateTimeStr(),REMARK,MERCHANT_KEY).toUpperCase());
		return payment;
	}

	public String getMac(String merchantId, String merchantUrl, Integer responseMode, String orderId, String currencyType, BigDecimal amount,
						  String assuredPay, String time, String remark, String merchantKey) {
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
		sb.append(time);
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
