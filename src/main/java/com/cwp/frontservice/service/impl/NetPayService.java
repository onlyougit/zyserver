package com.cwp.frontservice.service.impl;

import com.cwp.entity.Fund;
import com.cwp.entity.FundDetail;
import com.cwp.entity.NetpayFlow;
import com.cwp.entity.Recharge;
import com.cwp.enums.FlowWay;
import com.cwp.enums.RechargeWay;
import com.cwp.frontservice.pojo.NetPayResponse;
import com.cwp.frontservice.pojo.PaymentInfo;
import com.cwp.frontservice.service.INetPayService;
import com.cwp.repository.FundDetailRepository;
import com.cwp.repository.FundRepository;
import com.cwp.repository.NetpayFlowRepository;
import com.cwp.repository.RechargeRepository;
import com.cwp.util.common.MD5Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class NetPayService implements INetPayService {

	@Value("${netpay.notifyUrl}")
	private String notifyUrl;
	@Value("${netpay.returnUrl}")
	private String returnUrl;
	public static final Logger log = LoggerFactory.getLogger(NetPayService.class);
	public static final String PLACE_ORDER_URL = "https://wanshangxing.com/index.php?app=smartepay";
	public static final String MERCHANT_ID = "362182";
	public static final String SECRET_KEY = "gm7udcu4bmk1jkrtme3v0mka2f84pxm9kkk3c0krpmjxsw6hsyii85kzkb5q";
	@Autowired
	private RestOperations restOperations;
	@Autowired
	private NetpayFlowRepository netpayFlowRepository;
	@Autowired
	private RechargeRepository rechargeRepository;
	@Autowired
	private FundRepository fundRepository;
	@Autowired
	private FundDetailRepository fundDetailRepository;
	
	
	@Override
	public PaymentInfo placeOrder(String customerId, BigDecimal amount) {
		log.info("支付接口请求开始>>>>>>>>>>>>>>>>>>>");
		PaymentInfo paymentInfo = createPaymentInfo(customerId, amount);
		NetpayFlow netpayFlow = new NetpayFlow();
		netpayFlow.setCustomerId(Integer.parseInt(customerId));
		netpayFlow.setMerchantId(MERCHANT_ID);
		netpayFlow.setOrderId(paymentInfo.getOrderNo());
		netpayFlow.setCreateTime(new Date());
		netpayFlow.setAmount(paymentInfo.getOrderAmount().toString());
		netpayFlowRepository.save(netpayFlow);
		return paymentInfo;
	}
	@Transactional
	@Override
	public boolean verification(NetPayResponse netPayResponse) {
		log.info("支付回调数据验证>>>>>>>>>>>>>>>>>>>");
		Integer amount = new BigDecimal(netPayResponse.getOrderAmount()).multiply(new BigDecimal("100")).intValue();
		//开始验证数据
		NetpayFlow netpayFlow = netpayFlowRepository.findByOrderId(netPayResponse.getOrderNo());
		log.info("responseAmount="+netPayResponse.getOrderAmount());
		if(null == netpayFlow ||
				!netPayResponse.getOrderNo().equalsIgnoreCase(netpayFlow.getOrderId())||
				amount.equals(netpayFlow.getAmount())){
			log.info("数据验证失败");
			return false;
		}
		//验签
		String signStr = createSignStr(netPayResponse.getMerchantId(),netPayResponse.getVersion(),netPayResponse.getLanguage(),
				netPayResponse.getSignType(),netPayResponse.getPayType(),netPayResponse.getIssuerId(),netPayResponse.getMchtOrderId(),
				netPayResponse.getOrderNo(),netPayResponse.getMerTranTime(),netPayResponse.getOrderAmount(),netPayResponse.getPayDatetime(),
				netPayResponse.getExt1(),netPayResponse.getExt2(),netPayResponse.getPayResult());
		String mySign = MD5Util.HEXAndMd5(signStr).toUpperCase();
		log.info("签名数据："+mySign);
		if(!mySign.equalsIgnoreCase(netPayResponse.getSignMsg())){
			log.info("验证签名失败");
			return false;
		}
		if(StringUtils.isEmpty(netpayFlow.getTransactionId())){
			log.info("数据验证通过，开始更细数据");
			//更新交易流水
			netpayFlow.setModifyTime(new Date());
			netpayFlow.setTransactionId(netPayResponse.getMchtOrderId());
			netpayFlowRepository.save(netpayFlow);
			//往充值表加一条数据
			Recharge recharge = new Recharge();
			recharge.setCustomerId(netpayFlow.getCustomerId());
			recharge.setOrderId(netPayResponse.getMchtOrderId());
			recharge.setRechargeAmount(new BigDecimal(netPayResponse.getOrderAmount()));
			recharge.setRechargeTime(new Date());
			recharge.setRechargeWay(RechargeWay.ONLINEBANKING.getCode());
			recharge.setRemark(RechargeWay.ONLINEBANKING.getText());
			rechargeRepository.save(recharge);
			//更余额
			Fund fund = fundRepository.findByCustomerId(netpayFlow.getCustomerId());
			if(null != fund){
				fund.setBalance(fund.getBalance().add(new BigDecimal(netPayResponse.getOrderAmount())).setScale(2,BigDecimal.ROUND_HALF_UP));
				fundRepository.save(fund);
				//添加资金明细
				FundDetail fundDetail = new FundDetail();
				fundDetail.setChangeAmount(new BigDecimal(netPayResponse.getOrderAmount()));
				fundDetail.setChangeTime(new Date());
				fundDetail.setCustomerId(netpayFlow.getCustomerId());
				fundDetail.setChargeAmount(fund.getBalance());
				fundDetail.setFlowWay(FlowWay.INCOME.getCode());
				fundDetail.setRemark("网银充值");
				fundDetailRepository.save(fundDetail);
			}
		}
		return true;
	}
	public PaymentInfo createPaymentInfo(String customerId, BigDecimal amount){
		PaymentInfo paymentInfo = new PaymentInfo();
		paymentInfo.setInputCharset(1);
		paymentInfo.setPickupUrl(returnUrl);
		paymentInfo.setReceiveUrl(notifyUrl);
		paymentInfo.setVersion("v1.0");
		paymentInfo.setLanguage(1);
		paymentInfo.setSignType(0);
		paymentInfo.setMerchantId(MERCHANT_ID);
		paymentInfo.setOrderNo(getCurrentDateTimeStr());
		paymentInfo.setOrderAmount(amount.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP).intValue());
		paymentInfo.setOrderCurrency("156");
		paymentInfo.setOrderDatetime(getCurrentDateTimeStr2());
		paymentInfo.setProductName("recharge");
		paymentInfo.setExt2(MERCHANT_ID);
		paymentInfo.setPayType(1);
		String signStr = createSignStr(paymentInfo.getInputCharset(),paymentInfo.getPickupUrl(),
				paymentInfo.getReceiveUrl(),paymentInfo.getVersion(),paymentInfo.getLanguage(),
				paymentInfo.getSignType(),paymentInfo.getMerchantId(),paymentInfo.getOrderNo(),
				paymentInfo.getOrderAmount(),paymentInfo.getOrderCurrency(),paymentInfo.getOrderDatetime(),
				paymentInfo.getProductName(),paymentInfo.getExt2(),paymentInfo.getPayType());
		paymentInfo.setSignMsg(MD5Util.HEXAndMd5(signStr).toUpperCase());
		return paymentInfo;
	}
	/**
	 * 生成响应签名原串
	 * @param merchantId
	 * @param version
	 * @param language
	 * @param signType
	 * @param payType
	 * @param issuerId
	 * @param mchtOrderId
	 * @param orderNo
	 * @param merTranTime
	 * @param orderAmount
	 * @param payDatetime
	 * @param ext1
	 * @param ext2
	 * @param payResult
	 * @return
	 */
	public String createSignStr(String merchantId,String version,String language,String signType,String payType,
			String issuerId,String mchtOrderId,String orderNo,String merTranTime,String orderAmount,
			String payDatetime,String ext1,String ext2,String payResult){
		StringBuilder sb = new StringBuilder();
		sb.append("merchantId="+merchantId+"&");
		sb.append("version="+version+"&");
		sb.append("language="+language+"&");
		sb.append("signType="+signType+"&");
		sb.append("payType="+payType+"&");
		if(!StringUtils.isEmpty(issuerId)){
			sb.append("issuerId="+issuerId+"&");
		}
		sb.append("mchtOrderId="+mchtOrderId+"&");
		sb.append("orderNo="+orderNo+"&");
		sb.append("merTranTime="+merTranTime+"&");
		sb.append("orderAmount="+orderAmount+"&");
		sb.append("payDatetime="+payDatetime+"&");
		if(!StringUtils.isEmpty(ext1)){
			sb.append("ext1="+ext1+"&");
		}
		if(!StringUtils.isEmpty(ext2)){
			sb.append("ext2="+ext2+"&");
		}
		sb.append("payResult="+payResult+"&");
		sb.append("key="+SECRET_KEY);
		log.info("响应签名原串："+sb.toString());
		return sb.toString();
	}
	/**
	 * 生成请求签名原串
	 */
	public String createSignStr(Integer  inputCharset,String  pickupUrl,String  receiveUrl,
								String  version,Integer  language,Integer  signType,String  merchantId,
								String  orderNo,Integer  orderAmount,String  orderCurrency,
								String  orderDatetime,String  productName,String  ext2,Integer  payType){
		StringBuilder sb = new StringBuilder();
		sb.append("inputCharset="+inputCharset+"&");
		sb.append("pickupUrl="+pickupUrl+"&");
		sb.append("receiveUrl="+receiveUrl+"&");
		sb.append("version="+version+"&");
		sb.append("language="+language+"&");
		sb.append("signType="+signType+"&");
		sb.append("merchantId="+merchantId+"&");
		sb.append("orderNo="+orderNo+"&");
		sb.append("orderAmount="+orderAmount+"&");
		sb.append("orderCurrency="+orderCurrency+"&");
		sb.append("orderDatetime="+orderDatetime+"&");
		sb.append("productName="+productName+"&");
		sb.append("ext2="+ext2+"&");
		sb.append("payType="+payType+"&");
		sb.append("key="+SECRET_KEY);
		return sb.toString();
	}
	public String getCurrentDateTimeStr(){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
	public String getCurrentDateTimeStr2(){
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMDDhhmmss");
        Date date = new Date();
        String timeString = dataFormat.format(date);
        return timeString;
    }
	/**
	 * post请求：
	 * 1、HttpEntity用于传递具体的参数值，而uriVariables则用于格式化Http地址。如：
	 * String url = "http://localhost/mirana-ee/app/{path}";
	 * Map<String, String> varParams = Maps.newHashMap();
	 * varParams.put("path", "login");
	 * ResponseEntity<String> response = client.postForEntity(url, requestEntity , String.class, varParams);
	 * 2、postForEntity是对exchange的简化，仅仅只需要减少HttpMethod.POST参数。如：
	 * ResponseEntity<String> response = client.postForEntity(url, requestEntity , String.class);
	 * 3、exchange既可以执行POST方法，还可以执行GET，所以应用最为广泛。
	 * 4、HttpEntity是对HTTP请求的封装，包含两部分，header与body，header用于设置请求头，而body则用于设置请求体。
	 * @param paymentInfo
	 * @return
	 */
	public String postRequest1(PaymentInfo paymentInfo){
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		params.add("inputCharset", paymentInfo.getInputCharset().toString());
		params.add("pickupUrl", paymentInfo.getPickupUrl());
		params.add("receiveUrl", paymentInfo.getReceiveUrl());
		params.add("version", paymentInfo.getVersion());
		params.add("language", paymentInfo.getLanguage().toString());
		params.add("signType", paymentInfo.getSignType().toString());
		params.add("merchantId", paymentInfo.getMerchantId());
		params.add("orderNo", paymentInfo.getOrderNo());
		params.add("orderAmount", paymentInfo.getOrderAmount().toString());
		params.add("orderCurrency", paymentInfo.getOrderCurrency());
		params.add("orderDatetime", paymentInfo.getOrderDatetime());
		params.add("productName", paymentInfo.getProductName());
		params.add("ext2", paymentInfo.getExt2());
		params.add("payType", paymentInfo.getPayType().toString());
		params.add("signMsg", paymentInfo.getSignMsg());
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<String> response = restOperations.exchange(PLACE_ORDER_URL,HttpMethod.POST, requestEntity, String.class);
		String netPayResponse = response.getBody();
		return netPayResponse;
	}
	public NetPayResponse postRequest(String uuid,String fee,String sign){
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		params.add("custid", MERCHANT_ID);
		params.add("out_trade_no", uuid);
		params.add("body", "hello");
		params.add("attach", "world");
		params.add("total_fee", fee.toString());
		params.add("notify_url", notifyUrl);
		params.add("return_url", returnUrl);
		params.add("sign", sign);
		//params.add("service", SERVICE);
		//params.add("type", TYPE);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		ResponseEntity<NetPayResponse> response = restOperations.exchange(PLACE_ORDER_URL,HttpMethod.POST, requestEntity, NetPayResponse.class);
		NetPayResponse netPayResponse = response.getBody();
		return netPayResponse;
	}
}
