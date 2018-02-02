package com.zyserver.frontservice.service;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.FundDetail;

import java.math.BigDecimal;

public interface IFundService {

	ResponseJson<Object> deposit(Integer customerId, BigDecimal amount);

	ResponseJson<Object> settlement(Integer customerId);

	ResponseJson<Object> queryBalance(Integer customerId);

	ResponseJson<Object> queryFundDetail(FundDetail fundDetail, Integer pageNumber, Integer pageSize);

	ResponseJson<Object> queryFundBalance(Integer customerId);
}