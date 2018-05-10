package com.zyserver.frontservice.pojo;

import java.math.BigDecimal;

public class PaymentResponse {
    private String merchantId;
    private Integer responseMode;
    private String orderId;
    private String currencyType;
    private BigDecimal amount;
    private String returnCode;
    private String returnMessage;
    private String merchantKey;
    private String mac;
    private String bankCode;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "merchantId='" + merchantId + '\'' +
                ", responseMode=" + responseMode +
                ", orderId='" + orderId + '\'' +
                ", currencyType='" + currencyType + '\'' +
                ", amount=" + amount +
                ", returnCode='" + returnCode + '\'' +
                ", returnMessage='" + returnMessage + '\'' +
                ", merchantKey='" + merchantKey + '\'' +
                ", mac='" + mac + '\'' +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }
}
