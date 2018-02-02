package com.cwp.frontservice.pojo;

import java.math.BigDecimal;

public class Summary {

	//当前权益
	private BigDecimal Balance;
	//上日权益
	private BigDecimal PreBalance;
	//可用资金
	private BigDecimal Available;
	//保证金
	private BigDecimal Margin;
	//开仓冻结保证金
	private BigDecimal MarginFrozen;
	//挂单冻结手续费
	private BigDecimal CommissionFrozen;
	//持仓浮动盈亏
	private BigDecimal PositionProfitFloat;
	//平仓浮动盈亏
	private BigDecimal CloseProfitFloat;
	//手续费
	private BigDecimal Commission;
	//持仓盯市盈亏
	private BigDecimal PositionProfit;
	//平仓盯市盈亏
	private BigDecimal CloseProfit;
	//入金
	private BigDecimal Deposit;
	//出金
	private BigDecimal Withdraw;
	//优先资金
	private BigDecimal Credit;
	//期初投入
	private BigDecimal BaseCapital;
	//历史最大权益
	private BigDecimal EverMaxBalance;
	//当日最大权益
	private BigDecimal MaxBalance;
	//基本手续费
	private BigDecimal BaseCommission;
	public BigDecimal getBalance() {
		return Balance;
	}
	public void setBalance(BigDecimal balance) {
		Balance = balance;
	}
	public BigDecimal getPreBalance() {
		return PreBalance;
	}
	public void setPreBalance(BigDecimal preBalance) {
		PreBalance = preBalance;
	}
	public BigDecimal getAvailable() {
		return Available;
	}
	public void setAvailable(BigDecimal available) {
		Available = available;
	}
	public BigDecimal getMargin() {
		return Margin;
	}
	public void setMargin(BigDecimal margin) {
		Margin = margin;
	}
	public BigDecimal getMarginFrozen() {
		return MarginFrozen;
	}
	public void setMarginFrozen(BigDecimal marginFrozen) {
		MarginFrozen = marginFrozen;
	}
	public BigDecimal getCommissionFrozen() {
		return CommissionFrozen;
	}
	public void setCommissionFrozen(BigDecimal commissionFrozen) {
		CommissionFrozen = commissionFrozen;
	}
	public BigDecimal getPositionProfitFloat() {
		return PositionProfitFloat;
	}
	public void setPositionProfitFloat(BigDecimal positionProfitFloat) {
		PositionProfitFloat = positionProfitFloat;
	}
	public BigDecimal getCloseProfitFloat() {
		return CloseProfitFloat;
	}
	public void setCloseProfitFloat(BigDecimal closeProfitFloat) {
		CloseProfitFloat = closeProfitFloat;
	}
	public BigDecimal getCommission() {
		return Commission;
	}
	public void setCommission(BigDecimal commission) {
		Commission = commission;
	}
	public BigDecimal getPositionProfit() {
		return PositionProfit;
	}
	public void setPositionProfit(BigDecimal positionProfit) {
		PositionProfit = positionProfit;
	}
	public BigDecimal getCloseProfit() {
		return CloseProfit;
	}
	public void setCloseProfit(BigDecimal closeProfit) {
		CloseProfit = closeProfit;
	}
	public BigDecimal getDeposit() {
		return Deposit;
	}
	public void setDeposit(BigDecimal deposit) {
		Deposit = deposit;
	}
	public BigDecimal getWithdraw() {
		return Withdraw;
	}
	public void setWithdraw(BigDecimal withdraw) {
		Withdraw = withdraw;
	}
	public BigDecimal getCredit() {
		return Credit;
	}
	public void setCredit(BigDecimal credit) {
		Credit = credit;
	}
	public BigDecimal getBaseCapital() {
		return BaseCapital;
	}
	public void setBaseCapital(BigDecimal baseCapital) {
		BaseCapital = baseCapital;
	}
	public BigDecimal getEverMaxBalance() {
		return EverMaxBalance;
	}
	public void setEverMaxBalance(BigDecimal everMaxBalance) {
		EverMaxBalance = everMaxBalance;
	}
	public BigDecimal getMaxBalance() {
		return MaxBalance;
	}
	public void setMaxBalance(BigDecimal maxBalance) {
		MaxBalance = maxBalance;
	}
	public BigDecimal getBaseCommission() {
		return BaseCommission;
	}
	public void setBaseCommission(BigDecimal baseCommission) {
		BaseCommission = baseCommission;
	}
	
	
}
