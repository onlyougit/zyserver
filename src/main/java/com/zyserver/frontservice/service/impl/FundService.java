package com.zyserver.frontservice.service.impl;

import com.zyserver.common.ApplicationError;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.Customer;
import com.zyserver.entity.Fund;
import com.zyserver.entity.FundDetail;
import com.zyserver.enums.FlowWay;
import com.zyserver.enums.FundsRemark;
import com.zyserver.frontservice.pojo.Result;
import com.zyserver.frontservice.pojo.Summary;
import com.zyserver.frontservice.service.IFundService;
import com.zyserver.repository.CustomerRepository;
import com.zyserver.repository.FundDetailRepository;
import com.zyserver.repository.FundRepository;
import com.zyserver.util.common.DateUtil;
import com.zyserver.util.common.HttpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FundService implements IFundService {

	public static final Logger log = LoggerFactory.getLogger(FundService.class);
	@Value("${https.param.sa}")
	private String sa;
	@Value("${https.param.sapass}")
	private String sapass;
	public static final String ZERO_STRING_VALUE = "0";
	public static final Integer ZERO_INT_VALUE = 0;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	FundRepository fundRepository;
	@Autowired
	FundDetailRepository fundDetailRepository;

	@Transactional
	@Override
	public ResponseJson<Object> deposit(Integer customerId, BigDecimal amount) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if (DateUtil.isHoliday()){
			Customer customer = customerRepository.findById(customerId);
			//判断余额是否足够
			if(customer.getFund().getBalance().compareTo(amount)<0){
				responseJson.setCode(ApplicationError.LACK_OF_FUNDS.getCode());
				responseJson.setMsg(ApplicationError.LACK_OF_FUNDS.getMessage());
				return responseJson;
			}
			Result depositResult = HttpsUtil.deposit(customer.getCustomerName(),amount.doubleValue(),sa,sapass);
			log.info("入金返回码："+depositResult.getError().getCode()+";返回消息："+depositResult.getError().getMessage());
			if(!"0".equals(depositResult.getError().getCode())){
				responseJson.setCode(ApplicationError.INCOME_DEPOSIT_FAILED.getCode());
				responseJson.setMsg(ApplicationError.INCOME_DEPOSIT_FAILED.getMessage());
				return responseJson;
			}
			//扣掉余额
			Fund fund = fundRepository.findByCustomerId(customerId);
			fund.setBalance(fund.getBalance().subtract(amount).setScale(2,BigDecimal.ROUND_HALF_UP));
			fundRepository.save(fund);
			//添加资金详情
			FundDetail fundDetail = new FundDetail();
			fundDetail.setChangeAmount(amount);
			fundDetail.setChangeTime(new Date());
			fundDetail.setCustomerId(customerId);
			fundDetail.setChargeAmount(fund.getBalance());
			fundDetail.setFlowWay(FlowWay.EXPEND.getCode());
			fundDetail.setRemark(FundsRemark.ENTRYMONEY.getText());
			fundDetailRepository.save(fundDetail);
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
	public ResponseJson<Object> settlement(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if (DateUtil.isHoliday()){
			Customer customer = customerRepository.findById(customerId);
			//查询盘中权益资金
			Result result = HttpsUtil.queryaccount(customer.getCustomerName(),sa,sapass);
			log.info("查询资金返回码："+result.getError().getCode()+";返回消息："+result.getError().getMessage());
			if(!"0".equals(result.getError().getCode())){
				responseJson.setCode(ApplicationError.EXPEND_DEPOSIT_FAILED.getCode());
				responseJson.setMsg(ApplicationError.EXPEND_DEPOSIT_FAILED.getMessage());
				return responseJson;
			}
			Summary summary = result.getSummary();
			//保证金和冻结保证金都为0才能结算
			if(summary.getMargin().compareTo(new BigDecimal(ZERO_STRING_VALUE))!=ZERO_INT_VALUE || summary.getMarginFrozen().compareTo(new BigDecimal(ZERO_STRING_VALUE))!=ZERO_INT_VALUE){
				responseJson.setCode(ApplicationError.EXPEND_DEPOSIT_FAILED.getCode());
				responseJson.setMsg(ApplicationError.EXPEND_DEPOSIT_FAILED.getMessage());
				return responseJson;
			}
			//劣后资金 = 当前权益 - 优先资金
			BigDecimal balance = summary.getBalance().subtract(summary.getCredit()).setScale(2,BigDecimal.ROUND_HALF_UP);
			if(balance.compareTo(new BigDecimal(ZERO_STRING_VALUE))==ZERO_INT_VALUE){
				return responseJson;
			}
			BigDecimal realBalance = balance.negate();
			//请求出金
			Result depositResult = HttpsUtil.deposit(customer.getCustomerName(),realBalance.doubleValue(),sa,sapass);
			log.info("结算返回码："+depositResult.getError().getCode()+";返回消息："+depositResult.getError().getMessage());
			if(!"0".equals(depositResult.getError().getCode())){
				responseJson.setCode(ApplicationError.INCOME_DEPOSIT_FAILED.getCode());
				responseJson.setMsg(ApplicationError.INCOME_DEPOSIT_FAILED.getMessage());
				return responseJson;
			}
			//余额增加劣后资金
			Fund fund = customer.getFund();
			fund.setBalance(fund.getBalance().add(balance).setScale(2,BigDecimal.ROUND_HALF_UP));
			fundRepository.save(fund);
			//添加资金详情
			FundDetail fundDetail = new FundDetail();
			fundDetail.setChangeAmount(balance);
			fundDetail.setChangeTime(new Date());
			fundDetail.setCustomerId(customerId);
			fundDetail.setChargeAmount(fund.getBalance());
			fundDetail.setFlowWay(FlowWay.INCOME.getCode());
			fundDetail.setRemark(FundsRemark.EXITMONEY.getText());
			fundDetailRepository.save(fundDetail);
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
	public ResponseJson<Object> queryBalance(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		if (DateUtil.isHoliday()){
			Customer customer = customerRepository.findById(customerId);
			Result result = HttpsUtil.queryaccount(customer.getCustomerName(),sa,sapass);
			log.info("查询资金返回码："+result.getError().getCode()+";返回消息："+result.getError().getMessage());
			if(!"0".equals(result.getError().getCode())){
				responseJson.setCode(ApplicationError.EXPEND_DEPOSIT_FAILED.getCode());
				responseJson.setMsg(ApplicationError.EXPEND_DEPOSIT_FAILED.getMessage());
				return responseJson;
			}
			Summary summary = result.getSummary();
			Fund fund = fundRepository.findByCustomerId(customerId);
			fund.setFlushTime(new Date());
			fund.setDepositBalance(summary.getBalance());
			fundRepository.save(fund);
			responseJson.setData(summary.getBalance());
		}else{
			log.info("非股票交易时间");
			responseJson.setCode(ApplicationError.TRADE_TIME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.TRADE_TIME_ERROR.getMessage());
			return responseJson;
		}

		return responseJson;
	}

	@Override
	public ResponseJson<Object> queryFundDetail(FundDetail fundDetail, Integer pageNumber, Integer pageSize) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		String [] array = dayMaxAndMinDate(fundDetail.getChangeTime());
		Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "id");
		Page<FundDetail> cwpFundsDetailsPage = fundDetailRepository.findAll(new Specification<FundDetail>(){
			@Override
			public Predicate toPredicate(javax.persistence.criteria.Root<FundDetail> root,
										 CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(null!=fundDetail.getCustomerId()){
					list.add(cb.equal(root.get("customerId").as(Integer.class), fundDetail.getCustomerId()));
				}
				if(null!=fundDetail.getFlowWay()){
					list.add(cb.equal(root.get("flowWay").as(Integer.class), fundDetail.getFlowWay()));
				}
				if(null != array){
					if(!StringUtils.isEmpty(array[1])){
						list.add(cb.lessThanOrEqualTo(root.get("changeTime").as(String.class), array[1]));
					}
					if(!StringUtils.isEmpty(array[0])){
						list.add(cb.greaterThanOrEqualTo(root.get("changeTime").as(String.class), array[0]));
					}
				}
				Predicate[] p = new Predicate[list.size()];
				return cb.and(list.toArray(p));
			}

		},pageable);
		responseJson.setData(cwpFundsDetailsPage);
		return responseJson;
	}

	@Override
	public ResponseJson<Object> queryFundBalance(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Fund fund = fundRepository.findByCustomerId(customerId);
		if(null == fund){
			responseJson.setData("");
		}else{
			responseJson.setData(fund.getBalance());
		}
		return responseJson;
	}

	public String[] dayMaxAndMinDate(Date date){
		if(null == date){
			return null;
		}
		String [] array = new String[2];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		String dateTime = dateStr.substring(0, 10);
		array[0]=dateTime+" 00:00:00";
		array[1]=dateTime +" 23:59:59";
		return array;
	}
}
