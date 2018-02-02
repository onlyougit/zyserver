package com.cwp.frontservice.service;

import java.util.TimeZone;

import com.cwp.entity.Customer;
import com.cwp.entity.TokenEntity;

public interface ITokenService {

	public Customer getCustomerByToken(String token, TimeZone timeZone);

	public TokenEntity generateCustomerToken(Integer customerId, TimeZone timeZone);
}
