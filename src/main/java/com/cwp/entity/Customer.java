package com.cwp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "t_customer")
public class Customer {
	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 用户的电话号码
	@Column(name = "customer_phone")
	private String customerPhone;
	// 账号
	@Column(name = "customer_name")
	private String customerName;
	// 密码
	@Column(name = "customer_password")
	private String customerPassword;
	// 用户真实姓名
	@Column(name = "customer_real_name")
	private String customerRealName;
	// 身份证号
	@Column(name = "customer_card_id")
	private String customerCardId;
	// 注册时间
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "regist_time")
	private Date registTime;
	// 用户的交易状态（禁止交易0、允许交易1）
	@Column(name = "status")
	private Integer status;
	//安全码
	@Column(name = "safe")
	private String safe;
	@Column(name = "agent_id")
	private Integer agentId;
	@Column(name = "business_id")
	private Integer businessId;
	@Column(name = "editor")
	private Integer editor;
	@Column(name = "edit_time")
	private Date editTime;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "customerId")
	@OneToOne(mappedBy="customer")
	private Fund fund;

	
	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public Integer getEditor() {
		return editor;
	}

	public void setEditor(Integer editor) {
		this.editor = editor;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public String getCustomerRealName() {
		return customerRealName;
	}

	public void setCustomerRealName(String customerRealName) {
		this.customerRealName = customerRealName;
	}

	public String getCustomerCardId() {
		return customerCardId;
	}

	public void setCustomerCardId(String customerCardId) {
		this.customerCardId = customerCardId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getSafe() {
		return safe;
	}

	public void setSafe(String safe) {
		this.safe = safe;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	
}
