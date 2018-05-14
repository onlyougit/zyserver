package com.zyserver;

import com.zyserver.util.common.MD5Util;
import com.zyserver.util.common.SMSUtil;

public class SmsTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("merchantId=");
        sb.append("2120180428164017001");
        sb.append("&merchantUrl=");
        sb.append("http://pay.cdx.com/account/payment/rest/unspay/");
        sb.append("&responseMode=");
        sb.append("2");
        sb.append("&orderId=");
        sb.append("1805141737480422");
        sb.append("&currencyType=");
        sb.append("CNY");
        sb.append("&amount=");
        sb.append("1");
        sb.append("&assuredPay=");
        sb.append("false");
        sb.append("&time=");
        sb.append("20180514173748");
        sb.append("&remark=");
        sb.append("");
        sb.append("&merchantKey=");
        sb.append("dy051226");
        String mac = MD5Util.HEXAndMd5(sb.toString());
        System.out.println(mac.toUpperCase());
    }
}
