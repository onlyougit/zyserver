package com.zyserver.frontservice.service;

import com.zyserver.common.ResponseJson;
import com.zyserver.entity.Customer;


public interface ILoginService {

	ResponseJson<Object> loginCheck(Customer customer);

	ResponseJson<Object> changePassword(Integer customerId, String prePassword,
			String nowPassword, String confirmPassword);

	ResponseJson<Object> registrationCustomer(Customer customer,String code);

	ResponseJson<Object> realNameAuthentication(Customer customer);

	String entryptPassword(String password, String safe);

	ResponseJson<Object> sendCode(String mobile,Integer type);

	ResponseJson<Object> checkCode(String mobile, String code);

	ResponseJson<Object> forgetPassword(String mobile, String nowPassword, String confirmPassword);

	ResponseJson<Object> queryCustomerByToken(String token);

	ResponseJson<Object> isRealName(Integer customerId);

    ResponseJson<Object> registrationByUserName(String phone, String userName, String password, String code);
}