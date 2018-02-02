package com.zyserver.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangweibin on 2017/11/14.
 */
@Entity(name = "t_verify_code")
public class VerifyCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String mobile;

    private String code;

    private Date createTime;

    private Date overdueTime;

    private Integer status;

    private Integer Type;

    public void setType(Integer type) {
        Type = type;
    }

    public Integer getType() {

        return Type;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return Id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getOverdueTime() {
        return overdueTime;
    }

    public Integer getStatus() {
        return status;
    }
}
