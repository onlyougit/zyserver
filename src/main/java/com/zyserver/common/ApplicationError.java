package com.zyserver.common;

public enum ApplicationError {

	FAILED("失败", "0"),
	SUCCESS("成功", "1"),
	PARAMETER_ERROR("参数错误", "2"),
	CUSTOMER_NAME_ERROR("用户名不存在", "1000"),
	CUSTOMER_PASSWORD_ERROR("密码错误", "1001"),
	PASSWORD_NOT_SAME("密码不一致", "1002"),
	PASSWORD_ERROR("原密码错误", "1003"),
	CUSTOMER_NAME_EXIST("用户名已存在", "1004"),
	CUSTOMER_PHONE_EXIST("手机号码已经被使用", "1005"),
	PARENT_ID_NOTEXIST("上级用户不存在", "1006"),
	CUSTOMER_ID_NOTEXIST("用户不存在", "1007"),
	NOT_REAL_NAME_AUTHENTICATION("未实名认证", "1008"),
	AGENT_NOT_EXIST("该代理商目前不能注册", "1009"),
	REGIST_FAILED("注册失败，请联系客服", "10010"),
	LOGIN_NAME_NOT_EXIST("登录账号不存在", "10011"),
	BALANCE_NOT_ENOUGH("余额不足", "2001"),
	NO_EXIST_POSITION("交易失败，请重试", "2002"),
	OPTIMISTIC_LOCK_ERROR("交易失败，请重试", "2003"),
	TRADE_TIME_ERROR("不是交易时间", "2004"),
	STOCK_CODE_ERROR("股票代码错误", "2005"),
	TODAY_CANNOT_SELLOUT("当日持仓不能卖出", "2006"),
	STOCK_DISABLED("该股票不能交易", "2007"),
	BANK_CARD_EXIST("此银行卡号已经被使用，添加失败！", "3001"),
	REAL_NAME_AUTHENTICATION("您的身份证已被占用", "3002"),
	WITHDRAWAL_ERROR("提款金额不对", "3003"),
	BANK_CARD_NOT_EXIST("银行卡不存在", "3004"),
	EXIST_HANDING("有正在处理的申请，请联系客户经理", "3005"),
	TOKEN_ERROR("认证失败","4001"),
	TOKEN_NOTEXIST("token不存在", "4002"),
	C_ERROR("报单失败","5001"),
	SMS_SEND_FAILED("短信发送失败","5002"),
	VERIFICATIO_CODE_ERROR("验证码无效","5003"),
	SEND_MOO_FREQUENTLY("验证码发送过于频繁","5004"),
	INCOME_DEPOSIT_FAILED("交易时间段限制，操作失败","6001"),
	EXPEND_DEPOSIT_FAILED("结算失败","6002"),
	LACK_OF_FUNDS("余额不足","6003"),
	NETPAY_ORDER_FAILED("支付失败","6004"),
	WECHAT_ORDER_FAILED("预支付订单生成失败","7001"),
	RECHARGE_AMOUNT_ERROR("充值金额必须大于100，必须是100的整数倍","8001"),
	;
	private String message;
	private String code;

	private ApplicationError(String message, String code) {
		this.message = message;
		this.code = code;
	}

	public static ApplicationError get(String code) {
		for (ApplicationError ae : ApplicationError.values()) {
			if (ae.getCode().equals(code)) {
				return ae;
			}
		}
		return null;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
