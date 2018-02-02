package com.cwp.entity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


@Entity(name = "t_fund")
public class Fund {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	// 客户ID
	@Column(name = "customer_id")
	private Integer customerId;
	// 投入金额
	@Column(name = "invest_amount")
	private Integer investAmount;
	// 申请金额
	@Column(name = "apply_amount")
	private Integer applyAmount;
	// 余额
	@Column(name = "balance",columnDefinition="decimal(11,2)")
	private BigDecimal balance;
	//备注
	@Column(name = "remark")
	private String remark;
	// 盘中权益资金
	@Column(name = "deposit_balance",columnDefinition="decimal(11,2)")
	private BigDecimal depositBalance;
	//刷新时间
	@Column(name = "flush_time")
	private Date flushTime;
	
	@OneToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private Customer customer;

	
	public Date getFlushTime() {
		return flushTime;
	}

	public void setFlushTime(Date flushTime) {
		this.flushTime = flushTime;
	}

	public void setDepositBalance(BigDecimal depositBalance) {
		this.depositBalance = depositBalance;
	}

	public BigDecimal getDepositBalance() {

		return depositBalance;
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

	public Integer getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Integer investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Integer applyAmount) {
		this.applyAmount = applyAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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
