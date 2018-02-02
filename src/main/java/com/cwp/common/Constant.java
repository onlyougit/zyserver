package com.cwp.common;

public class Constant {

	public static final String OPTIMISTIC_LOCK_ERROR = "乐观锁异常";
	
	public static final Integer TOKEN_EXPIRES = 100;
	
	public static final String CURRENT_USER = "current_user";
	
	public static final String PARAM_TOKEN = "client_token";
	
	public static final Integer SAFE_SIZE = 8;
	//密码加密次数
	public static final int HASH_INTERATIONS = 1024;

	//短信验证码有效时间
	public static final Long CODE_REGISTER_PHONE = (long) (10*60*1000);

	//限制短信发送，每个号码最多次数
	public static final int MAX_SEND_COUNT = 5;
	
	
	public static final String PAY_URL = "https://wanshangxing.com/index.php?app=smartepay";
}
