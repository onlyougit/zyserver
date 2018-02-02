package com.zyserver.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BuyLimit {

	ONE(0, "10000","1万"),
	TWO(1, "20000","2万"),
    THREE(2, "30000","3万"),
    FIVE(3, "50000","5万"),
    TEN(4, "100000","10万"),
    TWENTY(5, "200000","20万"),
    THIRTY(6, "300000","30万"),
    FIFTY(7, "500000","50万"),

    ;

    /* 编码 */
    private Integer code;

    /* 描述 */
    private String text;

    private String enText;

    BuyLimit(Integer code, String text,String enText) {
        this.code = code;
        this.text = text;
        this.enText=enText;
    }

    public void setEnText(String enText) {
        this.enText = enText;
    }

    public String getEnText() {

        return enText;
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
    public static BuyLimit getEnums(Integer code) {
        for (BuyLimit enums : values()) {
            if (code==enums.getCode()) {
                return enums;
            }
        }
        return null;
    }


}
