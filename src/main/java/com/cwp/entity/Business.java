package com.cwp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Entity(name = "t_business")
public class Business {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "login_id")
	private Integer loginId;
	@Column(name = "business_name")
	private String businessName;
	@Column(name = "service_charge_standard",columnDefinition="decimal(11,2)")
	private BigDecimal serviceChargeStandard;
	@Column(name = "service_charge_cost",columnDefinition="decimal(11,2)")
	private BigDecimal serviceChargeCost;
	@Column(name = "bond_standard")
	private Integer bondStandard;
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	public BigDecimal getServiceChargeStandard() {
		return serviceChargeStandard;
	}
	public void setServiceChargeStandard(BigDecimal serviceChargeStandard) {
		this.serviceChargeStandard = serviceChargeStandard;
	}
	public BigDecimal getServiceChargeCost() {
		return serviceChargeCost;
	}
	public void setServiceChargeCost(BigDecimal serviceChargeCost) {
		this.serviceChargeCost = serviceChargeCost;
	}
	public Integer getBondStandard() {
		return bondStandard;
	}
	public void setBondStandard(Integer bondStandard) {
		this.bondStandard = bondStandard;
	}

}
