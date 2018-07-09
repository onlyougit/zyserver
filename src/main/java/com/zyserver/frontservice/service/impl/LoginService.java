package com.zyserver.frontservice.service.impl;

import com.zyserver.common.ApplicationError;
import com.zyserver.common.Constant;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.*;
import com.zyserver.enums.CustomerStatus;
import com.zyserver.enums.VerifyCodeStatus;
import com.zyserver.enums.VerifyCodeType;
import com.zyserver.frontservice.pojo.Result;
import com.zyserver.frontservice.service.ILoginService;
import com.zyserver.frontservice.service.ITokenService;
import com.zyserver.repository.*;
import com.zyserver.util.common.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Service
public class LoginService implements ILoginService {

	@Value("${https.param.sa}")
	private String sa;
	@Value("${https.param.sapass}")
	private String sapass;
	public static final Logger log = LoggerFactory.getLogger(LoginService.class);
	public static final String DEFAULT_ZERO = "0";
	public static final Integer INT_DEFAULT_ZERO = 0;
	public static final String DEFAULT_ONE = "1";
	//发送短信成功
	public static final String SEND_SUCCESS = "success";
    @Autowired
    CustomerRepository cwpCustomerRepository;
    @Autowired
    FundRepository cwpFundsRepository;
    @Autowired
	ITokenService tokenService;
	@Autowired
	VerifyCodeRepository cwpVerifyCodeRepository;
	@Autowired
	TokenRepository cwpTokenRepository;
	@Autowired
	private AgentRepository agentRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public ResponseJson<Object> loginCheck(Customer cwpCustomer) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Map<String, Object> map = new HashMap<>();
		Customer cwpCustomers = cwpCustomerRepository.findByCustomerPhoneAndStatus(cwpCustomer.getCustomerPhone(),CustomerStatus.EFFECTIVE.getCode());
		if (null == cwpCustomers) {
			responseJson.setCode(ApplicationError.CUSTOMER_NAME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_NAME_ERROR.getMessage());
			return responseJson;
		}
		if(!comparePassword(cwpCustomer.getCustomerPassword(),cwpCustomers.getCustomerPassword(),cwpCustomers.getSafe())){
			responseJson.setCode(ApplicationError.CUSTOMER_PASSWORD_ERROR.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_PASSWORD_ERROR.getMessage());
			return responseJson;
		}
		//生成Token
		TimeZone timeZone = LocaleContextHolder.getTimeZone();
		TokenEntity tokenEntity = tokenService.generateCustomerToken(cwpCustomers.getId(), timeZone);
		map.put("token", tokenEntity.getToken());
		map.put("cwpCustomers", cwpCustomers);
		responseJson.setData(map);
		return responseJson;
	}
	@Transactional
	@Override
	public ResponseJson<Object> changePassword(Integer customerId,
			String prePassword, String nowPassword, String confirmPassword) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(!nowPassword.equals(confirmPassword)){
			responseJson.setCode(ApplicationError.PASSWORD_NOT_SAME.getCode());
			responseJson.setMsg(ApplicationError.PASSWORD_NOT_SAME.getMessage());
			return responseJson;
		}
		Customer cwpCustomers = cwpCustomerRepository.findByIdAndStatus(customerId,CustomerStatus.EFFECTIVE.getCode());
		if (null == cwpCustomers) {
			responseJson.setCode(ApplicationError.CUSTOMER_ID_NOTEXIST.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_ID_NOTEXIST.getMessage());
			return responseJson;
		}
		if(!comparePassword(prePassword,cwpCustomers.getCustomerPassword(),cwpCustomers.getSafe())){
			responseJson.setCode(ApplicationError.PASSWORD_ERROR.getCode());
			responseJson.setMsg(ApplicationError.PASSWORD_ERROR.getMessage());
			return responseJson;
		}
		cwpCustomerRepository.updatePassword(customerId,entryptPassword(nowPassword,cwpCustomers.getSafe()));
		return responseJson;
	}

    @Transactional
	@Override
	public ResponseJson<Object> registrationCustomer(Customer cwpCustomer,String code) {
    	ResponseJson<Object> responseJson = new ResponseJson<>();
		if (DateUtil.isHoliday()){
			//判断手机号是否已经被注册
			Customer cwpCustomer1 = cwpCustomerRepository.findByCustomerPhone(cwpCustomer.getCustomerPhone());
			if(cwpCustomer1!=null){
				responseJson.setCode(ApplicationError.CUSTOMER_PHONE_EXIST.getCode());
				responseJson.setMsg(ApplicationError.CUSTOMER_PHONE_EXIST.getMessage());
				return responseJson;
			}
			//判断验证码是否正确
			responseJson = checkCode(cwpCustomer.getCustomerPhone(),code);
			if(!DEFAULT_ONE.equals(responseJson.getCode())){
				return responseJson;
			}
			//查询代理商
			Agent agent = agentRepository.findOne(cwpCustomer.getAgentId());
			//启用的代理才能注册
			if(null == agent || agent.getStatus()==0){
				responseJson.setCode(ApplicationError.AGENT_NOT_EXIST.getCode());
				responseJson.setMsg(ApplicationError.AGENT_NOT_EXIST.getMessage());
				return responseJson;
			}
			String group = "";
			String source1 = "";
			if("QL81035".equalsIgnoreCase(sa)){
				User user = userRepository.findOne(agent.getLoginId());
				group = user.getUserName();
				source1 = "81035999";
				log.info("sa="+sa+";pass="+sapass+";group="+user.getUserName());
			}
			log.info("sa="+sa+";pass="+sapass);
			Result accountResult = HttpsUtil.createaccount(cwpCustomer.getCustomerPassword(),cwpCustomer.getCustomerPhone(),sa,sapass,group);
			log.info("开户返回码："+accountResult.getError().getCode()+";返回消息："+accountResult.getError().getMessage());
			if(!"0".equals(accountResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result bondResult= HttpsUtil.setmarginrate(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass,source1);
			log.info("保证金返回码："+bondResult.getError().getCode()+";返回消息："+bondResult.getError().getMessage());
			if(!"0".equals(bondResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result serviceChargeResult = HttpsUtil.setcommissionrate(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass);
			log.info("手续费返回码："+serviceChargeResult.getError().getCode()+";返回消息："+serviceChargeResult.getError().getMessage());
			if(!"0".equals(serviceChargeResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result riskResult = HttpsUtil.setriskcontrol(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass,source1);
			log.info("风控返回码："+riskResult.getError().getCode()+";返回消息："+riskResult.getError().getMessage());
			if(!"0".equals(riskResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			cwpCustomer.setBusinessId(agent.getBusinessId());
			cwpCustomer.setCustomerName(accountResult.getAccount());
			cwpCustomer.setRegistTime(new Date());
			cwpCustomer.setStatus(CustomerStatus.EFFECTIVE.getCode());
			cwpCustomer.setSafe(getSafe());
			cwpCustomer.setCustomerPassword(entryptPassword(cwpCustomer.getCustomerPassword(),cwpCustomer.getSafe()));
			cwpCustomerRepository.save(cwpCustomer);
			//往资金表中插入一条记录
			Fund cwpFunds = new Fund();
			cwpFunds.setCustomerId(cwpCustomer.getId());
			cwpFunds.setBalance(new BigDecimal(DEFAULT_ZERO));
			cwpFunds.setApplyAmount(INT_DEFAULT_ZERO);
			cwpFunds.setInvestAmount(INT_DEFAULT_ZERO);
			cwpFunds.setDepositBalance(new BigDecimal(DEFAULT_ZERO));
			cwpFundsRepository.save(cwpFunds);
		}else{
			log.info("非股票交易时间");
			responseJson.setCode(ApplicationError.TRADE_TIME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.TRADE_TIME_ERROR.getMessage());
			return responseJson;
		}
		return responseJson;
	}

	@Transactional
	@Override
	public ResponseJson<Object> realNameAuthentication(Customer customer) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Customer cwpCustomer = cwpCustomerRepository.findByCustomerCardId(customer.getCustomerCardId());
		if(null != cwpCustomer){
			responseJson.setCode(ApplicationError.REAL_NAME_AUTHENTICATION.getCode());
			responseJson.setMsg(ApplicationError.REAL_NAME_AUTHENTICATION.getMessage());
			return responseJson;
		}
		cwpCustomerRepository.updateCustomerRealNameAndCustomerCardId(customer.getId(),customer.getCustomerCardId(),customer.getCustomerRealName());
		return responseJson;
	}

	@Transactional
	@Override
	public ResponseJson<Object> sendCode(String mobile,Integer type) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity();;
		if(type == 1){//注册
			Customer cwpCustomer = cwpCustomerRepository.findByCustomerPhone(mobile);
			verifyCodeEntity.setType(VerifyCodeType.REGISTERED.getCode());
			if(null != cwpCustomer){
				responseJson.setCode(ApplicationError.CUSTOMER_PHONE_EXIST.getCode());
				responseJson.setMsg(ApplicationError.CUSTOMER_PHONE_EXIST.getMessage());
				return responseJson;
			}
		}else if(type == 2){//找回密码
			Customer cwpCustomer = cwpCustomerRepository.findByCustomerPhone(mobile);
			verifyCodeEntity.setType(VerifyCodeType.UPDATE_PASSWORD.getCode());
			if(null == cwpCustomer){
				responseJson.setCode(ApplicationError.CUSTOMER_ID_NOTEXIST.getCode());
				responseJson.setMsg(ApplicationError.CUSTOMER_ID_NOTEXIST.getMessage());
				return responseJson;
			}
		}
		//把该号码以前的验证码状态设为无效
		VerifyCodeEntity verifyCodeEntity2 = cwpVerifyCodeRepository.queryByMobile(mobile);
		if(null != verifyCodeEntity2){
			verifyCodeEntity2.setStatus(VerifyCodeStatus.INVALID.getCode());
			cwpVerifyCodeRepository.save(verifyCodeEntity2);
		}
		String code = RandomUtil.getRandom(6);
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("您的验证码为：");
		stringBuilder.append(code);
		stringBuilder.append("，该验证码10分钟内有效，请勿泄漏于他人。【起点】");
		boolean flag = preventSendSMS(mobile);
		if(!flag){
			responseJson.setCode(ApplicationError.SEND_MOO_FREQUENTLY.getCode());
			responseJson.setMsg(ApplicationError.SEND_MOO_FREQUENTLY.getMessage());
			return responseJson;
		}
		try {
			String result = SMSUtil.sendSMS(stringBuilder.toString(),mobile);
			if(result.contains(SEND_SUCCESS)){
				Date date = new Date();
				verifyCodeEntity.setMobile(mobile);
				verifyCodeEntity.setCode(code);
				verifyCodeEntity.setCreateTime(date);
				verifyCodeEntity.setOverdueTime(new Date(date.getTime()+Constant.CODE_REGISTER_PHONE));
				verifyCodeEntity.setStatus(VerifyCodeStatus.EFFECTIVE.getCode());

				cwpVerifyCodeRepository.save(verifyCodeEntity);
			}else{
				responseJson.setCode(ApplicationError.SMS_SEND_FAILED.getCode());
				responseJson.setMsg(ApplicationError.SMS_SEND_FAILED.getMessage());
				return responseJson;
			}
		} catch (Exception e) {
			log.warn("短信发送失败："+e.getMessage());
			responseJson.setCode(ApplicationError.SMS_SEND_FAILED.getCode());
			responseJson.setMsg(ApplicationError.SMS_SEND_FAILED.getMessage());
			return responseJson;
		}
		return responseJson;
	}

	@Override
	public ResponseJson<Object> checkCode(String mobile, String code) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		VerifyCodeEntity verifyCodeEntity = cwpVerifyCodeRepository.queryByMobileAndCodeAndStatus(mobile,code,VerifyCodeStatus.EFFECTIVE.ordinal(),new Date());
		if(null == verifyCodeEntity){
			responseJson.setCode(ApplicationError.VERIFICATIO_CODE_ERROR.getCode());
			responseJson.setMsg(ApplicationError.VERIFICATIO_CODE_ERROR.getMessage());
			return responseJson;
		}else{
			verifyCodeEntity.setStatus(VerifyCodeStatus.INVALID.ordinal());
			cwpVerifyCodeRepository.save(verifyCodeEntity);
		}
		return responseJson;
	}

	@Override
	public ResponseJson<Object> forgetPassword(String mobile, String nowPassword, String confirmPassword) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if(!nowPassword.equals(confirmPassword)){
			responseJson.setCode(ApplicationError.PASSWORD_NOT_SAME.getCode());
			responseJson.setMsg(ApplicationError.PASSWORD_NOT_SAME.getMessage());
			return responseJson;
		}
		Customer cwpCustomers = cwpCustomerRepository.findByCustomerPhone(mobile);
		if (null == cwpCustomers) {
			responseJson.setCode(ApplicationError.CUSTOMER_ID_NOTEXIST.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_ID_NOTEXIST.getMessage());
			return responseJson;
		}
		cwpCustomerRepository.updatePassword(cwpCustomers.getId(),entryptPassword(nowPassword,cwpCustomers.getSafe()));
		return responseJson;
	}

	@Override
	public ResponseJson<Object> queryCustomerByToken(String token) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		TokenEntity tokenEntity = cwpTokenRepository.findByToken(token);
		if(null != tokenEntity){
			Customer cwpCustomer = cwpCustomerRepository.findById(tokenEntity.getCustomerId());
			if(null != cwpCustomer){
				//CwpFunds cwpFunds = cwpFundsRepository.findByCustomerId(cwpCustomer.getCustomerId());
				//cwpCustomer.setCwpFunds(cwpFunds);
				cwpCustomer.setSafe("");
				cwpCustomer.setCustomerPassword("");
				responseJson.setData(cwpCustomer);
			}
		}else{
			responseJson.setCode(ApplicationError.TOKEN_NOTEXIST.getCode());
			responseJson.setMsg(ApplicationError.TOKEN_NOTEXIST.getMessage());
			return responseJson;
		}
		return responseJson;
	}

	@Override
	public ResponseJson<Object> isRealName(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Customer cwpCustomer = cwpCustomerRepository.findById(customerId);
		if(null == cwpCustomer ||
				StringUtils.isEmpty(cwpCustomer.getCustomerRealName()) ||
				StringUtils.isEmpty(cwpCustomer.getCustomerCardId())){
			responseJson.setCode(ApplicationError.NOT_REAL_NAME_AUTHENTICATION.getCode());
			responseJson.setMsg(ApplicationError.NOT_REAL_NAME_AUTHENTICATION.getMessage());
			return responseJson;
		}
		Customer customer2 = new Customer();
		customer2.setCustomerRealName(cwpCustomer.getCustomerRealName());
		customer2.setCustomerCardId(cwpCustomer.getCustomerCardId());
		responseJson.setData(customer2);
		return responseJson;
	}

	@Override
	@Transactional
	public ResponseJson<Object> registrationByUserName(String phone, String userName, String password, String code) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if (DateUtil.isHoliday()){
			//判断手机号是否已经被注册
			Customer customer = cwpCustomerRepository.findByCustomerPhone(phone);
			if(customer!=null){
				responseJson.setCode(ApplicationError.CUSTOMER_PHONE_EXIST.getCode());
				responseJson.setMsg(ApplicationError.CUSTOMER_PHONE_EXIST.getMessage());
				return responseJson;
			}
			//判断验证码是否正确
			responseJson = checkCode(phone,code);
			if(!DEFAULT_ONE.equals(responseJson.getCode())){
				return responseJson;
			}
			//查询User
			User user1 = userRepository.findByUserName(userName);
			if(null == user1 || user1.getStatus().equalsIgnoreCase("INVALID")){
				responseJson.setCode(ApplicationError.LOGIN_NAME_NOT_EXIST.getCode());
				responseJson.setMsg(ApplicationError.LOGIN_NAME_NOT_EXIST.getMessage());
				return responseJson;
			}
			//查询代理商
			Agent agent = agentRepository.findByLoginId(user1.getUserId());
			//启用的代理才能注册
			if(null == agent || agent.getStatus()==0){
				responseJson.setCode(ApplicationError.AGENT_NOT_EXIST.getCode());
				responseJson.setMsg(ApplicationError.AGENT_NOT_EXIST.getMessage());
				return responseJson;
			}
			String group = "";
			String source1 = "";
			if("QL81035".equalsIgnoreCase(sa)){
				User user = userRepository.findOne(agent.getLoginId());
				group = user.getUserName();
				source1 = "81035999";
				log.info("sa="+sa+";pass="+sapass+";group="+user.getUserName());
			}
			log.info("sa="+sa+";pass="+sapass);
			Result accountResult = HttpsUtil.createaccount(password,phone,sa,sapass,group);
			log.info("开户返回码："+accountResult.getError().getCode()+";返回消息："+accountResult.getError().getMessage());
			if(!"0".equals(accountResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result bondResult= HttpsUtil.setmarginrate(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass,source1);
			log.info("保证金返回码："+bondResult.getError().getCode()+";返回消息："+bondResult.getError().getMessage());
			if(!"0".equals(bondResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result serviceChargeResult = HttpsUtil.setcommissionrate(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass);
			log.info("手续费返回码："+serviceChargeResult.getError().getCode()+";返回消息："+serviceChargeResult.getError().getMessage());
			if(!"0".equals(serviceChargeResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Result riskResult = HttpsUtil.setriskcontrol(agent.getTemplateAccount(),accountResult.getAccount(),sa,sapass,source1);
			log.info("风控返回码："+riskResult.getError().getCode()+";返回消息："+riskResult.getError().getMessage());
			if(!"0".equals(riskResult.getError().getCode())){
				responseJson.setCode(ApplicationError.REGIST_FAILED.getCode());
				responseJson.setMsg(ApplicationError.REGIST_FAILED.getMessage());
				return responseJson;
			}

			Customer customer1 = new Customer();
			customer1.setCustomerPhone(phone);
			customer1.setAgentId(agent.getId());
			customer1.setBusinessId(agent.getBusinessId());
			customer1.setCustomerName(accountResult.getAccount());
			customer1.setRegistTime(new Date());
			customer1.setStatus(CustomerStatus.EFFECTIVE.getCode());
			customer1.setSafe(getSafe());
			customer1.setCustomerPassword(entryptPassword(password,customer1.getSafe()));
			cwpCustomerRepository.save(customer1);
			//往资金表中插入一条记录
			Fund cwpFunds = new Fund();
			cwpFunds.setCustomerId(customer1.getId());
			cwpFunds.setBalance(new BigDecimal(DEFAULT_ZERO));
			cwpFunds.setApplyAmount(INT_DEFAULT_ZERO);
			cwpFunds.setInvestAmount(INT_DEFAULT_ZERO);
			cwpFunds.setDepositBalance(new BigDecimal(DEFAULT_ZERO));
			cwpFundsRepository.save(cwpFunds);
		}else{
			log.info("非股票交易时间");
			responseJson.setCode(ApplicationError.TRADE_TIME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.TRADE_TIME_ERROR.getMessage());
			return responseJson;
		}
		return responseJson;
	}

	/**
	 * 获取安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 * @return
	 */
	private String getSafe() {
		byte[] safe = Digests.generateSafe(Constant.SAFE_SIZE);
		return Encodes.encodeHex(safe);
	}
	/**
	 * 根据用户密钥加密密码
	 * @param password
	 * @param safe
	 * @return
	 */
	@Override
	public String entryptPassword(String password, String safe) {
		byte[] salt = Encodes.decodeHex(safe);
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt, Constant.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword); // 加密
	}


	/**
	 * 登录密码验证
	 * @param password
	 * @param encodePassword
	 * @param saltStr
	 * @return
	 */
	public boolean comparePassword(String password, String encodePassword, String saltStr) {
		byte[] salt = Encodes.decodeHex(saltStr);
		byte[] hashPassword = Digests.sha1(password.getBytes(), salt, Constant.HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword).equals(encodePassword);
	}

	public boolean preventSendSMS(String mobile){
		//计算离上一次刷的时间间隔
		/*VerifyCodeEntity verifyCodeEntity = cwpVerifyCodeRepository.queryByMobile(mobile);
		if(null != verifyCodeEntity){
			Long date = verifyCodeEntity.getCreateTime().getTime()+Constant.CODE_REGISTER_PHONE;
			if(new Date().getTime()<date){
				return false;
			}
		}*/
		//计算今天刷了几次
		int count = cwpVerifyCodeRepository.queryCountByMobile(mobile);
		if(count>Constant.MAX_SEND_COUNT){
			return false;
		}
		return true;
	}

	public static <T> T parseFromXml(Class<T> clazz, String xml) {
		//创建解析XML对象
		XStream xStream = new XStream(new DomDriver());
		//处理注解
		xStream.processAnnotations(clazz);
		@SuppressWarnings("unchecked")
		//将XML字符串转为bean对象
				T t = (T)xStream.fromXML(xml);
		return t;
	}
}
