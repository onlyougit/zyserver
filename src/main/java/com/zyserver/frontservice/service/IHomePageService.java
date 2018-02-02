package com.zyserver.frontservice.service;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.FundDetail;


public interface IHomePageService {

	ResponseJson<Object> queryFunds(Integer customerId);

	ResponseJson<Object> queryFundsDetails(FundDetail cwpFundsDetails,Integer pageNumber,Integer pageSize);

	ResponseJson<Object> addDrawApplication(Integer customerId,String amountMoney,String bankCardId);
}