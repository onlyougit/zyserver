package com.zyserver;

import com.zyserver.util.common.SMSUtil;

public class SmsTest {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("wangwb");
        stringBuilder.append("(");
        stringBuilder.append("13655971120");
        stringBuilder.append(")");
        stringBuilder.append("正在申请银行卡审核");
        try {
            SMSUtil.sendSMS(stringBuilder.toString(),"13655971120");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
