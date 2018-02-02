package com.cwp.frontservice.pojo;

import java.math.BigDecimal;

public class WeChatRequest {
	//公众账号ID,String(32)微信支付分配的公众账号ID（企业号corpid即为此appId）
	private String appid;// 公众账号 ID appid 是 String(32) 微信分配的公众账号 ID
	//商户号,String(32)微信支付分配的商户号
	private String mch_id;
	//设备号,String(32)PC网页或公众号内支付可以传"WEB"
	private String device_info;
	//随机字符串，String(32)长度要求在32位以内
	private String nonce_str;
	//签名,String(32)
	private String sign;
	//商品描述，String(128),腾讯充值中心-QQ会员充值
	private String body;
	//附加数据,String(127),深圳分店
	private String attach;
	//商户订单号,String(32)要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
	private String out_trade_no;
	//标价金额,Int,订单总金额，单位为分
	private BigDecimal total_fee;
	//终端IP，String(16)APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	private String spbill_create_ip;
	//交易起始时间,String(14)格式为yyyyMMddHHmmss
	private String time_start;
	//交易结束时间,String(14)格式为yyyyMMddHHmmss
	private String time_expire;
	//通知地址,String(256)异步接收微信支付结果通知的回调地址，不能携带参数。
	private String notify_url;
	//交易类型，String(16)JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
	private String trade_type;// 是 String(16) JSAPI、NATIVE、APP
	//用户标识,String(128),trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。
	private String openid;
	//商品ID，String(32)trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	private String product_id;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getTime_start() {
		return time_start;
	}
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	public String getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	
	
}
