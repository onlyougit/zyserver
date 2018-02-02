package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WithdrawalStatus {
	PENDING(1, "未处理"),
	PROCESSING(2, "处理中"),
	FINISH(3, "处理完成"),
	REFUSE(4, "拒绝"),
	FAILED(5, "处理失败"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    WithdrawalStatus(Integer code, String text) {
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
    public static WithdrawalStatus getEnums(Integer code) {
        for (WithdrawalStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
