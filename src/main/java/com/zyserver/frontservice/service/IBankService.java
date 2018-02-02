package com.zyserver.frontservice.service;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.BankCard;


public interface IBankService {
 
	ResponseJson<Object> addCwpBankCard(BankCard cwpBankCard);

	ResponseJson<Object> queryBankCard(Integer customerId);
}