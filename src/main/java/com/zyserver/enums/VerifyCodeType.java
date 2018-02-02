package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum VerifyCodeType {

	REGISTERED(1, "注册"),
	UPDATE_PASSWORD(2, "修改密码"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    VerifyCodeType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }
    public Integer getCode() {
        return code;
    }


    public void setCode(Integer code) {
		this.code = code;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
        return text;
    }
    public static VerifyCodeType getEnums(Integer code) {
        for (VerifyCodeType enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
