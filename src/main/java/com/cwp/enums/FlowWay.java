package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum FlowWay {
	
	EXPEND(0, "支出"),
	INCOME(1, "收入"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    FlowWay(Integer code, String text) {
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
    public static FlowWay getEnums(Integer code) {
        for (FlowWay enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
