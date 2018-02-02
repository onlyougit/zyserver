package com.cwp.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EntrustStatus {
	
	WAIT_SUBMIT(0, "待提交"),
	GENERATE_SCHEME(1, "生成方案"),
    PURCHASE_INVOKE_SUCCESS(2, "买入报单成功"),
    PURCHASE_INVOKE_FAILED(3, "买入报单失败"),
    SELLOUT_INVOKE_SUCCESS(4, "卖出报单成功"),
    SELLOUT_RECEIVE_FAILED(5, "卖出报单失败"),
    SUCCESS(6, "已成交"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    EntrustStatus(Integer code, String text) {
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
    public static EntrustStatus getEnums(Integer code) {
        for (EntrustStatus enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
