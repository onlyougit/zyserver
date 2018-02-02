package com.zyserver.util.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangweibin on 2017/11/13.
 */
public class SMSUtil {
	public static final Logger log = LoggerFactory.getLogger(SMSUtil.class);
    private final static String ENCODE = "UTF-8";
    //private final static String encode = "GBK"; //页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8
    private final static String USERNAME = "yinglibao";
    private final static String PASSWORD_MD5 = MD5Util.HEXAndMd5("Wangwb5689");
    private final static String APIKEY = "55a29ea462f9af5fc0820c902b3f4f1e";
    public static String sendSMS(String content,String mobile) throws Exception{
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时：30秒
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");//读取超时：30秒
        StringBuffer buffer = new StringBuffer();
        //如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
        String contentUrlEncode = URLEncoder.encode(content,ENCODE);
        buffer.append("http://m.5c.com.cn/api/send/index.php?username=");
        buffer.append(USERNAME);
        buffer.append("&password_md5=");
        buffer.append(PASSWORD_MD5);
        buffer.append("&mobile=");
        buffer.append(mobile);//发多个号码：13800000001,13800000002,...N 。
        buffer.append("&apikey=");
        buffer.append(APIKEY);
        buffer.append("&content=");//要发送的短信内容，特别注意：签名必须设置，网页验证码应用需要加添加【图形识别码】。
        buffer.append(contentUrlEncode);
        buffer.append("&encode=");
        buffer.append(ENCODE);
        URL url = new URL(buffer.toString());
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String result = reader.readLine();
        log.info("短信发送结果："+result);
        return result;
    }
}
