package com.zyserver.frontservice.pojo;

import java.math.BigDecimal;

public class Payment {
    //固定值online_pay
    private String service;

    private String merchantId;

    private String notifyUrl;
    //支付成功跳转URL
    private String returnUrl;
    //参数编码字符集
    private String charset;
    //商品的名称
    private String title;
    //商品的具体描述
    private String body;
    //商户订单号
    private String orderNo;
    //单位为RMB元
    private BigDecimal totalFee;
    //支付类型，固定值为1
    private String paymentType;
    //支付方式，directPay：直连模式；bankPay：收银台模式
    private String paymethod;
    //网银代码，当支付方式为bankPay时，该值为空；支付方式为directPay时该值必传
    private String defaultbank;
    //接入方式，当该值传“app”时，表示app接入，返回二维码地址，需商户自行生成二维码；值为“web”时，表示web接入，直接在收银台页面上显示二维码；值为“H5”时，表示手机端html5接入，会在手机端唤醒支付app
    private String isApp;

    private String buyerEmail;
    //签名方式 ：SHA
    private String signType;

    private String sign;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymethod() {
        return paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getDefaultbank() {
        return defaultbank;
    }

    public void setDefaultbank(String defaultbank) {
        this.defaultbank = defaultbank;
    }

    public String getIsApp() {
        return isApp;
    }

    public void setIsApp(String isApp) {
        this.isApp = isApp;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
