package com.zyserver.frontservice.controler;

import com.zyserver.annotation.NeedSignIn;
import com.zyserver.common.ApplicationError;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.FundDetail;
import com.zyserver.frontservice.service.IHomePageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/serverInterface/homePage")
public class HomePageController {
	Log log = LogFactory.getLog(HomePageController.class);
	@Autowired
	private IHomePageService homePageService;

	/**
	 * 查询资金表通过客户ID
	 * @param customerId
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value="/queryFunds",method=RequestMethod.POST)
	public ResponseJson<Object> queryFunds(Integer customerId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  homePageService.queryFunds(customerId);
		return responseJson;
	}


	/**
	 * 查询资金明细列表
	 * @param cwpFundsDetails
	 * @param pageNumber
	 * @param pageSize
     * @return
     */
	@NeedSignIn
	@RequestMapping(value="/queryFundsDetails",method=RequestMethod.POST)
	public ResponseJson<Object> queryFundsDetails(FundDetail cwpFundsDetails,Integer pageNumber,Integer pageSize){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(cwpFundsDetails) ||
				StringUtils.isEmpty(cwpFundsDetails.getCustomerId()) ||
				StringUtils.isEmpty(pageNumber) ||
				StringUtils.isEmpty(pageSize) ){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  homePageService.queryFundsDetails(cwpFundsDetails,pageNumber,pageSize);
    	return responseJson;
	}
	
	/**
	 * 提现申请
	 * @param customerId
	 * @param amountMoney
	 * @param bankCardId
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value = "/withdrawalsApply",method=RequestMethod.POST)
    public ResponseJson<Object> withdrawalsApply(Integer customerId,String amountMoney,String bankCardId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		BigDecimal amount = new BigDecimal(amountMoney);
		if(StringUtils.isEmpty(customerId) ||
				StringUtils.isEmpty(amountMoney) ||
				StringUtils.isEmpty(bankCardId) ||
				amount.compareTo(new BigDecimal("50000"))>0){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = homePageService.addDrawApplication(customerId,amountMoney,bankCardId);
    	return responseJson;
    }
}
