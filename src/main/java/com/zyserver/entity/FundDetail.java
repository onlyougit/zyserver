package com.zyserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Yanly on 2017/8/26.
 */
@Entity(name = "t_fund_detail")
public class FundDetail {
	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 客户id
	@Column(name = "customer_id")
	private Integer customerId;
	// 流向（收入或支出）
	@Column(name = "flow_way")
	private Integer flowWay;
	// 变动金额
	@Column(name = "change_amount", columnDefinition = "decimal(11,2)")
	private BigDecimal changeAmount;
	// 剩余金额
	@Column(name = "charge_amount", columnDefinition = "decimal(11,2)")
	private BigDecimal chargeAmount;
	// 变动时间
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "change_time")
	private Date changeTime;
	// 备注信息（记录变动原因）
	@Column(name = "remark")
	private String remark;
	// 客户
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
	public Integer getFlowWay() {
		return flowWay;
	}
	public void setFlowWay(Integer flowWay) {
		this.flowWay = flowWay;
	}
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
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
