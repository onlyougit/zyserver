package com.cwp.frontservice.service.impl;

import com.cwp.common.ApplicationError;
import com.cwp.common.ResponseJson;
import com.cwp.entity.BankCard;
import com.cwp.enums.AuditStatus;
import com.cwp.frontservice.service.IBankService;
import com.cwp.frontservice.service.ILoginService;
import com.cwp.repository.BankCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankService implements IBankService {

	public static final String DEFAULT_ONE = "1";
    @Autowired
    BankCardRepository cwpBankCardRepository;
	@Autowired
	ILoginService loginService;

	
	@Transactional
	@Override
	public ResponseJson<Object> addCwpBankCard(BankCard cwpBankCard) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		//判断客户是否已认证
		responseJson = loginService.isRealName(cwpBankCard.getCustomerId());
		if(!DEFAULT_ONE.equals(responseJson.getCode())){
			return responseJson;
		}
		//判断银行卡是否已经被添加
		BankCard ca =  cwpBankCardRepository.findByBankCardId(cwpBankCard.getBankCardId());
		if(ca!=null){
			responseJson.setCode(ApplicationError.BANK_CARD_EXIST.getCode());
			responseJson.setMsg(ApplicationError.BANK_CARD_EXIST.getMessage());
			return responseJson;
		}
		cwpBankCard.setAuditStatus(AuditStatus.WAIT_AUDIT.getCode());
        cwpBankCardRepository.save(cwpBankCard);
		return responseJson;
	}

	@Override
	public ResponseJson<Object> queryBankCard(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		List<BankCard> cwpBankCardList = cwpBankCardRepository.findByCustomerId(customerId);
		responseJson.setData(cwpBankCardList);
		return responseJson;
	}
}
