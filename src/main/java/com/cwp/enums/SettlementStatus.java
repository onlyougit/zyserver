package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SettlementStatus {

	INVALID(0, "失败"),
	EFFECTIVE(1, "成功"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    SettlementStatus(Integer code, String text) {
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
    public static SettlementStatus getEnums(Integer code) {
        for (SettlementStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
