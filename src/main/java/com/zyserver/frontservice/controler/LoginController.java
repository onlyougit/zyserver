package com.zyserver.frontservice.controler;

import com.zyserver.annotation.NeedSignIn;
import com.zyserver.common.ApplicationError;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.Customer;
import com.zyserver.frontservice.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serverInterface/login")
public class LoginController {
	public static final Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private ILoginService loginService;

	/**
	 * 用户登录
	 * @param cwpCustomer
	 * @return
	 */
	@RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
	public ResponseJson<Object> loginCheck(Customer cwpCustomer) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(cwpCustomer) ||
				StringUtils.isEmpty(cwpCustomer.getCustomerPhone()) ||
				StringUtils.isEmpty(cwpCustomer.getCustomerPassword())){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.loginCheck(cwpCustomer);
		return responseJson;
	}
	/**
	 * 修改密码
	 * @param customerId
	 * @param prePassword
	 * @param nowPassword
	 * @param confirmPassword
	 * @return
	 */
	@NeedSignIn
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseJson<Object> loginCheck(Integer customerId,String prePassword,String nowPassword,String confirmPassword ) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customerId) ||
				StringUtils.isEmpty(prePassword) ||
				StringUtils.isEmpty(nowPassword) ||
				StringUtils.isEmpty(confirmPassword)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.changePassword(customerId,prePassword,nowPassword,confirmPassword);
		return responseJson;
	}
	/**
	 * 用户注册
	 * @param customer
	 * @return
     */
	@RequestMapping(value = "/registrationCustomer",method=RequestMethod.POST)
    public ResponseJson<Object> registrationCustomer(Customer customer,String code){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customer) ||
				StringUtils.isEmpty(customer.getCustomerPhone()) ||
				StringUtils.isEmpty(customer.getCustomerPassword()) ||
				StringUtils.isEmpty(customer.getAgentId()) ||
				StringUtils.isEmpty(code)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.registrationCustomer(customer,code);
    	return responseJson;
    }

	/**
	 * 用户实名认证
	 * @param customer
     * @return
     */
	@NeedSignIn
	@RequestMapping(value = "/realNameAuthentication",method=RequestMethod.POST)
    public ResponseJson<Object> realNameAuthentication(Customer customer){
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(StringUtils.isEmpty(customer) ||
				StringUtils.isEmpty(customer.getId()) ||
				StringUtils.isEmpty(customer.getCustomerCardId()) ||
				StringUtils.isEmpty(customer.getCustomerRealName())){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
    	responseJson = loginService.realNameAuthentication(customer);
    	return responseJson;
    }

	/**
	 * 验证码发送
	 * @param mobile
	 * @return
     */
	@RequestMapping(value = "/sendCode", method=RequestMethod.POST)
	public ResponseJson<Object> sendCode(String mobile,Integer type) {
		ResponseJson<Object> responseJson = new ResponseJson<Object>();
		if(StringUtils.isEmpty(mobile) ||
				StringUtils.isEmpty(type)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.sendCode(mobile,type);
		return responseJson;
	}

	/**
	 * 验证码校验
	 * @param mobile
	 * @param code
     * @return
     */
	@RequestMapping(value = "/checkCode", method=RequestMethod.POST)
	public ResponseJson<Object> checkCode(String mobile,String code) {
		ResponseJson<Object> responseJson = new ResponseJson<Object>();
		if(StringUtils.isEmpty(mobile) ||
				StringUtils.isEmpty(code)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.checkCode(mobile,code);
		return responseJson;
	}

	/**
	 * 忘记密码
	 * @param mobile
	 * @param nowPassword
	 * @param confirmPassword
     * @return
     */
	@RequestMapping(value = "/forgetPassword", method=RequestMethod.POST)
	public ResponseJson<Object> forgetPassword(String mobile,String nowPassword,String confirmPassword) {
		ResponseJson<Object> responseJson = new ResponseJson<Object>();
		if(StringUtils.isEmpty(mobile) ||
				StringUtils.isEmpty(nowPassword) ||
				StringUtils.isEmpty(confirmPassword)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.forgetPassword(mobile,nowPassword,confirmPassword);
		return responseJson;
	}

	/**
	 * token获取客户信息
	 * @param token
	 * @return
     */
	@RequestMapping(value = "/queryCustomerByToken", method=RequestMethod.POST)
	public ResponseJson<Object> queryCustomerByToken(String token) {
		ResponseJson<Object> responseJson = new ResponseJson<Object>();
		if(StringUtils.isEmpty(token)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.queryCustomerByToken(token);
		return responseJson;
	}

	/**
	 * 判断是否实名认证
	 * @param customerId
	 * @return
     */
	@RequestMapping(value = "/isRealName", method=RequestMethod.POST)
	public ResponseJson<Object> isRealName(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<Object>();
		if(StringUtils.isEmpty(customerId)){
			responseJson.setCode(ApplicationError.PARAMETER_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PARAMETER_ERROR.getMessage());
			return responseJson;
		}
		responseJson = loginService.isRealName(customerId);
		return responseJson;
	}
}
