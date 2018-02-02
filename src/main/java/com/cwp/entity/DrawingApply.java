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

/**
 * Created by Yanly on 2017/8/26.
 */
@Entity(name = "t_drawing_apply")
public class DrawingApply {
	// 提款ID(编号)
	@Id
    @Column(name="id",updatable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	// 提款金额
	@Column(name = "drawing_amount", columnDefinition = "decimal(11,2)")
	private BigDecimal drawingAmount;
	// 申请时间
	@Column(name = "apply_time")
	private Date applyTime;
	// 客户ID
	@Column(name = "customer_id")
	private Integer customerId;
	// 银行（具体到支行，银行信息拼接）
	@Column(name = "bank")
	private String bank;
	// 卡号
	@Column(name = "bank_card_id")
	private String bankCardId;
	// 处理状态(1:未处理;2:处理中;3:处理完成;4:拒绝;5:处理失败;)
	@Column(name = "status")
	private Integer status;
	// 操作时间
	@Column(name = "operation_time")
	private Date operatingTime;
	// 操作人
	@Column(name = "operator")
	private String operator;
	// 备注
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


	public BigDecimal getDrawingAmount() {
		return drawingAmount;
	}

	public void setDrawingAmount(BigDecimal drawingAmount) {
		this.drawingAmount = drawingAmount;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOperatingTime() {
		return operatingTime;
	}

	public void setOperatingTime(Date operatingTime) {
		this.operatingTime = operatingTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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
