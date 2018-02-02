package com.zyserver.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangweibin on 2017/12/24.
 */
@Entity(name = "t_history_fund")
public class HistoryFund {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // 资金ID
    @Column(name = "fund_id")
    private Integer fundId;
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
    @Column(name = "deposit_balance",columnDefinition="decimal(11,2)")
    private BigDecimal depositBalance;
    //备注
    @Column(name = "remark")
    private String remark;
    //创建时间
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "flush_time")
    private Date flushTime;

    public void setFlushTime(Date flushTime) {
        this.flushTime = flushTime;
    }

    public Date getFlushTime() {

        return flushTime;
    }

    public void setDepositBalance(BigDecimal depositBalance) {
        this.depositBalance = depositBalance;
    }

    public BigDecimal getDepositBalance() {

        return depositBalance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getFundId() {

        return fundId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setInvestAmount(Integer investAmount) {
        this.investAmount = investAmount;
    }

    public void setApplyAmount(Integer applyAmount) {
        this.applyAmount = applyAmount;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {

        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getInvestAmount() {
        return investAmount;
    }

    public Integer getApplyAmount() {
        return applyAmount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getRemark() {
        return remark;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
