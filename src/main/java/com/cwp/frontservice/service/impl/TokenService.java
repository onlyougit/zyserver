package com.cwp.frontservice.service.impl;

import java.time.ZonedDateTime;
import java.util.TimeZone;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cwp.common.Constant;
import com.cwp.entity.Customer;
import com.cwp.entity.TokenEntity;
import com.cwp.frontservice.service.ITokenService;
import com.cwp.repository.CustomerRepository;
import com.cwp.repository.TokenRepository;

@Service("tokenService")
public class TokenService implements ITokenService {

	@Autowired
	private TokenRepository cwpTokenRepository;
	
	@Autowired
	private CustomerRepository cwpCustomerRepository;
	
	@Override
	public Customer getCustomerByToken(String token, TimeZone timeZone) {
		TokenEntity tokenEntity = cwpTokenRepository.findByToken(token);
		if (tokenEntity != null) {
			String zdtStr = tokenEntity.getExpiresTime();
			ZonedDateTime zdt = ZonedDateTime.parse(zdtStr);
			ZonedDateTime now = ZonedDateTime.now(timeZone.toZoneId());
			if (now.isBefore(zdt)) {
				Customer customer = cwpCustomerRepository.findById(tokenEntity.getCustomerId());
				return customer;
			}
		}
		return null;
	}

	@Override
	public TokenEntity generateCustomerToken(Integer customerId, TimeZone timeZone){
		Customer customer = cwpCustomerRepository.findById(customerId);
		if (customer == null) {
		}
		TokenEntity customerToken = new TokenEntity();
		customerToken.setCustomerId(customerId);
		ZonedDateTime now = ZonedDateTime.now(timeZone.toZoneId());
		ZonedDateTime expiresTime = now.plusDays(Constant.TOKEN_EXPIRES);
		customerToken.setCreateTime(now.toString());
		customerToken.setExpiresTime(expiresTime.toString());
		String token = generateToken();
		customerToken.setToken(token);
		cwpTokenRepository.save(customerToken);
		return customerToken;
	}

	private String generateToken() {
		return UUID.randomUUID().toString();
	}

}
