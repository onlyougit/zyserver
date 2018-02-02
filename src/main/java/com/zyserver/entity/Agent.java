package com.zyserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;


@Entity(name = "t_agent")
public class Agent {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "login_id")
	private Integer loginId;
	@Column(name = "business_id")
	private Integer businessId;
	@Column(name = "agent_name")
	private String agentName;
	@Column(name = "agent_phone")
	private String agentPhone;
	@Column(name = "service_charge_standard",columnDefinition="decimal(11,2)")
	private BigDecimal serviceChargeStandard;
	@Column(name = "service_charge_cost",columnDefinition="decimal(11,2)")
	private BigDecimal serviceChargeCost;
	@Column(name = "template_account")
	private String templateAccount;
	@Column(name = "bond_standard")
	private Integer bondStandard;
	@Column(name = "change_person")
	private Integer changePerson;
	@Column(name = "status")
	private Integer status;
	
	public Integer getLoginId() {
		return loginId;
	}
	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentPhone() {
		return agentPhone;
	}
	public void setAgentPhone(String agentPhone) {
		this.agentPhone = agentPhone;
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
	public String getTemplateAccount() {
		return templateAccount;
	}
	public void setTemplateAccount(String templateAccount) {
		this.templateAccount = templateAccount;
	}
	public Integer getBondStandard() {
		return bondStandard;
	}
	public void setBondStandard(Integer bondStandard) {
		this.bondStandard = bondStandard;
	}
	public Integer getChangePerson() {
		return changePerson;
	}
	public void setChangePerson(Integer changePerson) {
		this.changePerson = changePerson;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
