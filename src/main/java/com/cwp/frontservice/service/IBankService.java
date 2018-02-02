package com.cwp.frontservice.service;

import com.cwp.common.ResponseJson;
import com.cwp.entity.BankCard;


public interface IBankService {
 
	ResponseJson<Object> addCwpBankCard(BankCard cwpBankCard);

	ResponseJson<Object> queryBankCard(Integer customerId);
}