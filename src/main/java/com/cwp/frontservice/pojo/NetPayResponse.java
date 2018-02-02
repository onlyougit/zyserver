package com.cwp.frontservice.pojo;

public class NetPayResponse {

	private String merchantId;
	
	private String version;
	
	private String language;
	
	private String signType;
	
	private String payType;
	
	private String issuerId;
	//订单号
	private String mchtOrderId;
	//原订单号
	private String orderNo;
	
	private String merTranTime;
	
	private String orderAmount;
	
	private String payDatetime;
	
	private String ext1;
	
	private String ext2;
	
	private String payResult;
	
	private String signMsg;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getMchtOrderId() {
		return mchtOrderId;
	}

	public void setMchtOrderId(String mchtOrderId) {
		this.mchtOrderId = mchtOrderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMerTranTime() {
		return merTranTime;
	}

	public void setMerTranTime(String merTranTime) {
		this.merTranTime = merTranTime;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getPayDatetime() {
		return payDatetime;
	}

	public void setPayDatetime(String payDatetime) {
		this.payDatetime = payDatetime;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	@Override
	public String toString() {
		return "NetPayResponse [merchantId=" + merchantId + ", version="
				+ version + ", language=" + language + ", signType=" + signType
				+ ", payType=" + payType + ", issuerId=" + issuerId
				+ ", mchtOrderId=" + mchtOrderId + ", orderNo=" + orderNo
				+ ", merTranTime=" + merTranTime + ", orderAmount="
				+ orderAmount + ", payDatetime=" + payDatetime + ", ext1="
				+ ext1 + ", ext2=" + ext2 + ", payResult=" + payResult
				+ ", signMsg=" + signMsg + "]";
	}
	
	
	
	
}
