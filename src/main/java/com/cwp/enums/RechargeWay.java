package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RechargeWay {

    ONLINEBANKING(0, "网银充值"),

    ALIPAY(1, "支付宝充值"),

    BACKEND(2, "后台充值"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
	RechargeWay(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public static RechargeWay getEnums(Integer code) {
        for (RechargeWay enums : values()) {
            if (code == enums.getCode()) {
                return enums;
            }
        }
        return null;
    }
}
