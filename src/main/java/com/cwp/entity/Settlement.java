package com.cwp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.util.Date;


@Entity(name = "t_settlement")
public class Settlement {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 客户ID
	@Column(name = "customer_id")
	private Integer customerId;
	@Column(name = "agent_id")
	private Integer agentId;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "daily_trade_number")
	private Integer dailyTradeNumber;
	@Column(name = "sum_daily_service_charge",columnDefinition="decimal(11,2)")
	private BigDecimal sumDailyServiceCharge;
	@Column(name = "sum_daily_service_charge_cost",columnDefinition="decimal(11,2)")
	private BigDecimal sumDailyServiceChargeCost;
	@Column(name = "daily_rebate",columnDefinition="decimal(11,2)")
	private BigDecimal dailyRebate;
	@Column(name = "status")
	private Integer status;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public BigDecimal getSumDailyServiceCharge() {
		return sumDailyServiceCharge;
	}
	public void setSumDailyServiceCharge(BigDecimal sumDailyServiceCharge) {
		this.sumDailyServiceCharge = sumDailyServiceCharge;
	}
	public BigDecimal getSumDailyServiceChargeCost() {
		return sumDailyServiceChargeCost;
	}
	public void setSumDailyServiceChargeCost(BigDecimal sumDailyServiceChargeCost) {
		this.sumDailyServiceChargeCost = sumDailyServiceChargeCost;
	}
	public BigDecimal getDailyRebate() {
		return dailyRebate;
	}
	public void setDailyRebate(BigDecimal dailyRebate) {
		this.dailyRebate = dailyRebate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDailyTradeNumber() {
		return dailyTradeNumber;
	}
	public void setDailyTradeNumber(Integer dailyTradeNumber) {
		this.dailyTradeNumber = dailyTradeNumber;
	}
}
