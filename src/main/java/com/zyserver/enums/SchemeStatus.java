package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SchemeStatus {
	
	MATCHING(0, "匹配中"),
	MATCH_FAILED(1, "匹配失败"),
    GETED(2, "已持仓"),
    OUTTING(3, "平仓中"),
    OUTTED(4, "已平仓"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    SchemeStatus(Integer code, String text) {
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
    public static SchemeStatus getEnums(Integer code) {
        for (SchemeStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
