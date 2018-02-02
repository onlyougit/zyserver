package com.zyserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "t_bank_card")
public class BankCard{
	
	@Id
	@Column(name = "id", updatable = false)
	private Integer id;
	// 客户ID
	@Column(name = "customer_id")
	private Integer customerId;
	// 开户银行名称
	@Column(name = "bank_name")
	private String bankName;
	// 开户银行所在省份
	@Column(name = "bank_province")
	private String bankProvince;
	// 开户银行所在城市
	@Column(name = "bank_city")
	private String bankCity;
	// 开户银行地址（手动填写）
	@Column(name = "bank_address")
	private String bankAddress;
	// 银行卡号
	@Column(name = "bank_card_id")
	private String bankCardId;
	// 审核状态(1:未审核;2:审核成功;3:审核失败;)
	@Column(name = "audit_status")
	private Integer auditStatus;
	// 客户
	@OneToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;
	
	
	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

}
