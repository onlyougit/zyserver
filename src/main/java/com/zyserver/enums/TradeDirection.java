package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TradeDirection {

    PURCHASE(0, "买入"),
    SELLOUT(1, "卖出"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    TradeDirection(Integer code, String text) {
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
    public static TradeDirection getEnums(Integer code) {
        for (TradeDirection enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
