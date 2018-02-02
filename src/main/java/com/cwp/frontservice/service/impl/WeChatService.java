package com.cwp.frontservice.service.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.cwp.common.ApplicationError;
import com.cwp.common.ResponseJson;
import com.cwp.entity.NetpayFlow;
import com.cwp.frontservice.pojo.WeChatAccount;
import com.cwp.frontservice.pojo.WeChatRequest;
import com.cwp.frontservice.service.IWeChatService;
import com.cwp.repository.NetpayFlowRepository;
import com.cwp.util.common.DateUtil;
import com.cwp.util.common.MD5Util;
import com.cwp.util.common.SendPost;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.xml.sax.InputSource;

@Service
public class WeChatService implements IWeChatService {
	public static final Logger log = LoggerFactory.getLogger(WeChatService.class);
	
	//统一下单接口链接
	private static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	@Autowired
	private WeChatAccount weChatAccount;
	@Autowired
	private NetpayFlowRepository netpayFlowRepository;
	
	/**
	 * 统一下单接口
	 */
	@Override
	public ResponseJson<Object> placeOrder(Integer customerId,BigDecimal amount,
			String tradeType, String ipAddr) throws Exception {
		/*if ("NATIVE".equals(tradeType)) {
			return getPCWeChatAccount();
		} else if ("APP".equals(tradeType)) {
			return getAPPWeChatAccount();
		}*/
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		BigDecimal fee = amount.multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP);
		//生成请求参数
		WeChatRequest weChatRequest = new WeChatRequest();
		weChatRequest.setAppid(weChatAccount.getAppId());
		weChatRequest.setMch_id(weChatAccount.getMchId());
		weChatRequest.setNonce_str(getRandomString(24));
		weChatRequest.setAttach("attach");
		weChatRequest.setOut_trade_no(uuid);
		weChatRequest.setTotal_fee(fee);
		weChatRequest.setSpbill_create_ip(ipAddr);
		weChatRequest.setNotify_url(weChatAccount.getNotifyUrl());
		weChatRequest.setTrade_type(tradeType);
		weChatRequest.setBody("SPTWIN");
		weChatRequest.setProduct_id(DateUtil.getProductId());
		weChatRequest.setSign("wechat:"+createSign(weChatRequest,weChatAccount.getPartnerKey()));
		Map<String, Object> result = this.unifiedorder(weChatRequest);
		if (result != null) {
			String code_url = (String) result.get("code_url");
			String prepay_id = (String) result.get("prepay_id");
			String sign = (String) result.get("sign");
			if(StringUtils.isEmpty(prepay_id) || StringUtils.isEmpty(sign)){
				log.info("预支付订单生成失败");
				responseJson.setCode(ApplicationError.WECHAT_ORDER_FAILED.getCode());
				responseJson.setMsg(ApplicationError.WECHAT_ORDER_FAILED.getMessage());
			}else{
				map.put("prepay_id", prepay_id);
				if (tradeType.equals("NATIVE")) {
					map.put("code_url", code_url);
					map.put("sign", sign);
					map.put("weChatPaymentNo", uuid);
				}
				//将数据添加到数据库
				
				log.info("预支付订单生成成功");
			}
		}else{
			responseJson.setCode(ApplicationError.WECHAT_ORDER_FAILED.getCode());
			responseJson.setMsg(ApplicationError.WECHAT_ORDER_FAILED.getMessage());
		}
		return responseJson;
	}

	/**
	 * 回调接口
	 */
	@Override
	public String orderCallback(String notityXml) throws Exception {
		String resXml = "";
		log.info("==============微信支付回调开始================");
		if (!StringUtils.isEmpty(notityXml)) {
			Map map = parseXmlToList2(notityXml);
			String return_code = map.get("return_code").toString();
			if ("SUCCESS".equalsIgnoreCase(return_code)) {// 返回状态为支付成功
				String result_code = map.get("result_code").toString();
				if ("SUCCESS".equalsIgnoreCase(result_code)) {
					String out_trade_no = map.get("out_trade_no").toString();// 微信支付流水号
					String transaction_id = map.get("transaction_id").toString();// 微信支付单号
					String time_end = map.get("time_end").toString();// 支付完成时间
					String total_fee = map.get("total_fee").toString();// 订单金额,单位为分
					if (out_trade_no != null) {
						NetpayFlow netpayFlow = netpayFlowRepository.findByTransactionId(out_trade_no);
						if (netpayFlow != null) {
							//业务处理
							resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
									+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
						} else {
							resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
									+ "<return_msg><![CDATA[订单号错误]]></return_msg>" + "</xml> ";
						}
					} else {
						resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
								+ "<return_msg><![CDATA[订单号为空]]></return_msg>" + "</xml> ";
					}
				}
			} else {

			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		log.info("==============微信支付回调结束================");
		return resXml;
	}
	private Map<String, Object> unifiedorder(WeChatRequest weChatRequest) throws Exception {
		StringBuffer xml = new StringBuffer("<xml>");
		xml.append("<appid>").append(weChatRequest.getAppid()).append("</appid>");
		xml.append("<mch_id>").append(weChatRequest.getMch_id()).append("</mch_id>");
		xml.append("<nonce_str>").append(weChatRequest.getNonce_str()).append("</nonce_str>");
		xml.append("<body><![CDATA[").append(new String(weChatRequest.getBody().getBytes(), "utf-8")).append("]]></body>");
		xml.append("<out_trade_no>").append(weChatRequest.getOut_trade_no()).append("</out_trade_no>");
		xml.append("<attach><![CDATA[").append(weChatRequest.getAttach()).append("]]></attach>");
		xml.append("<total_fee>").append(weChatRequest.getTotal_fee()).append("</total_fee>");
		xml.append("<spbill_create_ip>").append(weChatRequest.getSpbill_create_ip()).append("</spbill_create_ip>");
		xml.append("<notify_url>").append(weChatRequest.getNotify_url()).append("</notify_url>");
		xml.append("<trade_type>").append(weChatRequest.getTrade_type()).append("</trade_type>");
		xml.append("<sign><![CDATA[").append(weChatRequest.getSign()).append("]]></sign>");
		xml.append("</xml>");
		log.info("请求报文：" + xml);
		log.info("-----------发送请求---------");
		//String responseString = SendPost.doPost(UNIFIED_ORDER, xml.toString());
		log.info("-----------接受响应---------");
		//String fromatString = new String(responseString.getBytes(), "utf-8");
		//log.info(fromatString);
		return null;//parseXmlToList2(fromatString);
	}
	private static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	/**
	 * 微信支付签名算法<br>
	 * 参数名ASCII码从小到大排序(字典序)<br>
	 * 如果参数的值为空不参与签名 参数名区分大小写<br>
	 * 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验
	 * 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
	 * 
	 * @param weChatRequest
	 * @return String
	 */
	public String createSign(WeChatRequest weChatRequest, String partnerkey) {
		Map<String, String> packageParams = new HashMap<String, String>();
		packageParams.put("appid", weChatRequest.getAppid());
		packageParams.put("mch_id", weChatRequest.getMch_id());
		packageParams.put("nonce_str", weChatRequest.getNonce_str());
		packageParams.put("body", weChatRequest.getBody());
		packageParams.put("attach", weChatRequest.getAttach());
		packageParams.put("out_trade_no", weChatRequest.getOut_trade_no());
		packageParams.put("total_fee", weChatRequest.getTotal_fee() + "");
		packageParams.put("spbill_create_ip", weChatRequest.getSpbill_create_ip());
		packageParams.put("notify_url", weChatRequest.getNotify_url());
		packageParams.put("trade_type", weChatRequest.getTrade_type());

		StringBuffer sb = new StringBuffer();
		Collection<String> keyset = packageParams.keySet();
		List<String> list = new ArrayList<String>(keyset);
		Collections.sort(list);
		for (String string : list) {
			if (null != string && !"".equals(string) && !"sign".equals(string) && !"key".equals(string)) {
				String value = packageParams.get(string);
				if (null != value && !"".equals(value) && !"null".equals(value)) {
					sb.append(string + "=" + value + "&");
				}
			}
		}
		sb.append("key=" + partnerkey);
		String sign = MD5Util.HEXAndMd5(sb.toString()).toUpperCase();
		return sign;
	}
	public String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
