package com.cwp.frontservice.service;

import com.cwp.common.ResponseJson;
import com.cwp.entity.FundDetail;


public interface IHomePageService {

	ResponseJson<Object> queryFunds(Integer customerId);

	ResponseJson<Object> queryFundsDetails(FundDetail cwpFundsDetails,Integer pageNumber,Integer pageSize);

	ResponseJson<Object> addDrawApplication(Integer customerId,String amountMoney,String bankCardId);
}