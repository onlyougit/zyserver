package com.cwp.frontservice.controler;

import com.cwp.annotation.NeedSignIn;
import com.cwp.common.ApplicationError;
import com.cwp.common.ResponseJson;
import com.cwp.entity.FundDetail;
import com.cwp.frontservice.service.IFundService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/serverInterface/fund")
public class FundController {
	Log log = LogFactory.getLog(FundController.class);
	@Autowired
	private IFundService fundService;

	/**
	 * 入金操作
	 * @param customerId
	 * @param amount
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value="/deposit",method=RequestMethod.POST)
	public ResponseJson<Object> deposit(Integer customerId,BigDecimal amount){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId) ||
				StringUtils.isEmpty(amount)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  fundService.deposit(customerId,amount);
		return responseJson;
	}
	/**
	 * 结算操作
	 * @param customerId
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value="/settlement",method=RequestMethod.POST)
	public ResponseJson<Object> settlement(Integer customerId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  fundService.settlement(customerId);
		return responseJson;
	}

	/**
	 * 查询盘中权益资金
	 * @param customerId
	 * @return
     */
	@NeedSignIn
	@RequestMapping(value="/queryBalance",method=RequestMethod.POST)
	public ResponseJson<Object> queryBalance(Integer customerId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  fundService.queryBalance(customerId);
		return responseJson;
	}

	/**
	 * 查询资金明细列表
	 * @param fundDetail
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value="/queryFundDetail",method=RequestMethod.POST)
	public ResponseJson<Object> queryFundDetail(FundDetail fundDetail, Integer pageNumber, Integer pageSize){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(fundDetail) ||
				StringUtils.isEmpty(fundDetail.getCustomerId()) ||
				StringUtils.isEmpty(pageNumber) ||
				StringUtils.isEmpty(pageSize) ){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  fundService.queryFundDetail(fundDetail,pageNumber,pageSize);
		return responseJson;
	}
	@NeedSignIn
	@RequestMapping(value="/queryFundBalance",method=RequestMethod.POST)
	public ResponseJson<Object> queryFundBalance(Integer customerId){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson =  fundService.queryFundBalance(customerId);
		return responseJson;
	}
}
