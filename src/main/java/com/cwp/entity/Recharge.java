package com.cwp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.math.BigDecimal;
import java.util.Date;


@Entity(name = "t_recharge")
public class Recharge {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 客户ID
	@Column(name = "customer_id")
	private Integer customerId;
	//支付订单号
	@Column(name = "order_id")
	private String orderId;
	// 投入金额
	@Column(name = "recharge_way")
	private Integer rechargeWay;
	// 申请金额
	@Column(name = "recharge_amount",columnDefinition="decimal(11,2)")
	private BigDecimal rechargeAmount;
	@Column(name = "recharge_time")
	private Date rechargeTime;
	//备注
	@Column(name = "remark")
	private String remark;
	
	@OneToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getRechargeWay() {
		return rechargeWay;
	}

	public void setRechargeWay(Integer rechargeWay) {
		this.rechargeWay = rechargeWay;
	}

	public BigDecimal getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(BigDecimal rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
