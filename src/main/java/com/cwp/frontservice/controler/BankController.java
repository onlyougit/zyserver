package com.cwp.frontservice.controler;

import com.cwp.annotation.NeedSignIn;
import com.cwp.common.ApplicationError;
import com.cwp.common.ResponseJson;
import com.cwp.entity.BankCard;
import com.cwp.frontservice.service.IBankService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serverInterface/bank")
public class BankController {
	Log log= LogFactory.getLog(LoginController.class);
	
	@Autowired
	IBankService registrationService;

	/**
	 * 添加银行卡
	 * @param cwpBankCard
     * @return
     */
	@NeedSignIn
	@RequestMapping(value = "/addBankCard",method=RequestMethod.POST)
    public ResponseJson<Object> addCwpBankCard(BankCard cwpBankCard){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(cwpBankCard) ||
				StringUtils.isEmpty(cwpBankCard.getCustomerId()) ||
				StringUtils.isEmpty(cwpBankCard.getBankCardId()) ||
				StringUtils.isEmpty(cwpBankCard.getBankName()) ||
				StringUtils.isEmpty(cwpBankCard.getBankProvince()) ||
				StringUtils.isEmpty(cwpBankCard.getBankCity()) ||
				StringUtils.isEmpty(cwpBankCard.getBankAddress())){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
    	responseJson = registrationService.addCwpBankCard(cwpBankCard);
    	return responseJson;
    }

	/**
	 * 查询银行卡通过客户ID
	 * @param customerId
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value="/queryBankCard",method=RequestMethod.POST)
	public ResponseJson<Object> queryBankCard(Integer customerId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  registrationService.queryBankCard(customerId);
		return responseJson;
	}
}