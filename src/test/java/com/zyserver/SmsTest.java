package com.zyserver;

import com.zyserver.util.common.MD5Util;
import com.zyserver.util.common.SMSUtil;

import java.math.BigDecimal;

public class SmsTest {
    public static void main(String[] args) {
        /*StringBuilder sb = new StringBuilder();
        sb.append("merchantId=");
        sb.append("2120180428164017001");
        sb.append("&responseMode=");
        sb.append("2");
        sb.append("&orderId=");
        sb.append("zy20180515100444");
        sb.append("&currencyType=");
        sb.append("CNY");
        sb.append("&amount=");
        sb.append("0.10");
        sb.append("&returnCode=");
        sb.append("0000");
        sb.append("&returnMessage=");
        sb.append("");
        sb.append("&merchantKey=");
        sb.append("dy051226");
        String mac = MD5Util.HEXAndMd5(sb.toString());
        System.out.println(mac.toUpperCase());*/
        BigDecimal bigDecimal = new BigDecimal("0.100");
        System.out.println(bigDecimal.toString());
    }
}
