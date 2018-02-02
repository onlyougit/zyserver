package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PositionStatus {
	
	CLOSE_POSITION(0, "已平仓"),
	POSITION(1, "已持仓"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    PositionStatus(Integer code, String text) {
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
    public static PositionStatus getEnums(Integer code) {
        for (PositionStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
