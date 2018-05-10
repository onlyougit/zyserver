package com.zyserver.frontservice.pojo;

import java.math.BigDecimal;

public class Payment {
    //固定值online_pay
    private String version;

    private String merchantId;

    private String merchantUrl;
    //响应方式
    private Integer responseMode;
    private String orderId;
    //货币类型
    private String currencyType;
    private BigDecimal amount;
    //是否通过银生担保支付
    private String assuredPay;
    private String time;
    private String remark;
    private String mac;
    private String merchantKey;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantUrl() {
        return merchantUrl;
    }

    public void setMerchantUrl(String merchantUrl) {
        this.merchantUrl = merchantUrl;
    }

    public Integer getResponseMode() {
        return responseMode;
    }

    public void setResponseMode(Integer responseMode) {
        this.responseMode = responseMode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAssuredPay() {
        return assuredPay;
    }

    public void setAssuredPay(String assuredPay) {
        this.assuredPay = assuredPay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }
}
