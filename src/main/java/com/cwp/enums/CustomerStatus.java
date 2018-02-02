package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CustomerStatus {
	
	INVALID(0, "不允许交易"),
	EFFECTIVE(1, "允许交易"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    CustomerStatus(Integer code, String text) {
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
    public static CustomerStatus getEnums(Integer code) {
        for (CustomerStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
