package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum AuditStatus {
	
	WAIT_AUDIT(0, "处理中"),
	AUDIT_PASS(1, "通过"),
	AUDIT_FAILED(2, "不通过"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    AuditStatus(Integer code, String text) {
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
    public static AuditStatus getEnums(Integer code) {
        for (AuditStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
