package com.zyserver.frontservice.service;

import java.util.TimeZone;

import com.zyserver.entity.Customer;
import com.zyserver.entity.TokenEntity;

public interface ITokenService {

	public Customer getCustomerByToken(String token, TimeZone timeZone);

	public TokenEntity generateCustomerToken(Integer customerId, TimeZone timeZone);
}
