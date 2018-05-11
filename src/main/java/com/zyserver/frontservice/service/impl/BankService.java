package com.zyserver.frontservice.service.impl;

import com.zyserver.common.ApplicationError;
import com.zyserver.common.Constant;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.BankCard;
import com.zyserver.entity.Customer;
import com.zyserver.enums.AuditStatus;
import com.zyserver.enums.CustomerStatus;
import com.zyserver.frontservice.service.IBankService;
import com.zyserver.frontservice.service.ILoginService;
import com.zyserver.repository.BankCardRepository;
import com.zyserver.repository.CustomerRepository;
import com.zyserver.util.common.SMSUtil;
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
	@Autowired
	CustomerRepository customerRepository;

	
	@Transactional
	@Override
	public ResponseJson<Object> addCwpBankCard(BankCard cwpBankCard) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Customer customer = customerRepository.findById(cwpBankCard.getCustomerId());
		if(null == customer || customer.getStatus() == CustomerStatus.INVALID.getCode()){
			responseJson.setCode(ApplicationError.CUSTOMER_NAME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_NAME_ERROR.getMessage());
			return responseJson;
		}
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
		//发送短信
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(customer.getCustomerRealName());
		stringBuilder.append("(");
		stringBuilder.append(customer.getCustomerPhone());
		stringBuilder.append(")");
		stringBuilder.append("正在申请银行卡审核");
		try {
			SMSUtil.sendSMS(stringBuilder.toString(), Constant.FINANCE_MOBILE);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
