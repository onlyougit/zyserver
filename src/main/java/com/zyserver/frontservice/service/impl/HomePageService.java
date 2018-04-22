package com.zyserver.frontservice.service.impl;

import com.zyserver.common.ApplicationError;
import com.zyserver.common.ResponseJson;
import com.zyserver.entity.*;
import com.zyserver.enums.CustomerStatus;
import com.zyserver.enums.WithdrawalStatus;
import com.zyserver.frontservice.service.IHomePageService;
import com.zyserver.repository.*;

import com.zyserver.util.common.SMSUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HomePageService implements IHomePageService {

    @Autowired
    BankCardRepository cwpBankCardRepository;
    @Autowired
    FundRepository cwpFundsRepository;
    @Autowired
	DrawingApplyRepository drawingApplyRepository;
	@Autowired
	FundDetailRepository cwpFundsDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;


	@Override
	public ResponseJson<Object> queryFundsDetails(FundDetail cwpFundsDetails,Integer pageNumber,Integer pageSize) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		String [] array = dayMaxAndMinDate(cwpFundsDetails.getChangeTime());
		Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "changeTime");
		Page<FundDetail> cwpFundsDetailsPage = cwpFundsDetailsRepository.findAll(new Specification<FundDetail>(){
			@Override
			public Predicate toPredicate(Root<FundDetail> root,
										 CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(null!=cwpFundsDetails.getCustomerId()){
					list.add(cb.equal(root.get("customerId").as(Integer.class), cwpFundsDetails.getCustomerId()));
				}
				if(null!=cwpFundsDetails.getFlowWay()){
					list.add(cb.equal(root.get("flowWay").as(Integer.class), cwpFundsDetails.getFlowWay()));
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
	@Override
	public ResponseJson<Object> queryFunds(Integer customerId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Fund cwpFunds = cwpFundsRepository.findByCustomerId(customerId);
		responseJson.setData(cwpFunds);
		return responseJson;
	}
	
	//提现申请保存
	@Transactional
	@Override
	public ResponseJson<Object> addDrawApplication(Integer customerId,String amountMoney,String bankCardId) {
		ResponseJson<Object> responseJson = new ResponseJson<>();
		Customer customer = customerRepository.findById(customerId);
		if(null == customer || customer.getStatus() == CustomerStatus.INVALID.getCode()){
			responseJson.setCode(ApplicationError.CUSTOMER_NAME_ERROR.getCode());
			responseJson.setMsg(ApplicationError.CUSTOMER_NAME_ERROR.getMessage());
			return responseJson;
		}
		//判断当前客户是否有未处理的申请记录，有就提款失败
		List<DrawingApply> drawingApplys = drawingApplyRepository.findByCustomerId(customerId);
		long count = drawingApplys.stream().filter(w->w.getStatus()==WithdrawalStatus.HANDING.getCode()).count();
		if(count>0){
			responseJson.setCode(ApplicationError.EXIST_HANDING.getCode());
			responseJson.setMsg(ApplicationError.EXIST_HANDING.getMessage());
			return responseJson;
		}
		//判断提款金额小于等于余额，并且大于等于10
		BigDecimal withdrawAmountMoney = new BigDecimal(amountMoney);
		Fund cwpFunds = cwpFundsRepository.findByCustomerId(customerId);
		if(withdrawAmountMoney.compareTo(cwpFunds.getBalance())>0 || withdrawAmountMoney.compareTo(new BigDecimal("10"))<0){
			responseJson.setCode(ApplicationError.WITHDRAWAL_ERROR.getCode());
			responseJson.setMsg(ApplicationError.WITHDRAWAL_ERROR.getMessage());
			return responseJson;
		}
		BankCard cwpBankCard = cwpBankCardRepository.findByBankCardId(bankCardId);
		if(null == cwpBankCard){
			responseJson.setCode(ApplicationError.BANK_CARD_NOT_EXIST.getCode());
			responseJson.setMsg(ApplicationError.BANK_CARD_NOT_EXIST.getMessage());
			return responseJson;
		}
		DrawingApply cwpWithdrawalApplication = new DrawingApply();
		cwpWithdrawalApplication.setDrawingAmount(withdrawAmountMoney);
		cwpWithdrawalApplication.setApplyTime(new Date());
		cwpWithdrawalApplication.setBankCardId(bankCardId);
		cwpWithdrawalApplication.setCustomerId(customerId);
		cwpWithdrawalApplication.setBank(cwpBankCard.getBankAddress());
		cwpWithdrawalApplication.setStatus(WithdrawalStatus.HANDING.getCode());
		drawingApplyRepository.save(cwpWithdrawalApplication);
		//发送短信
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("客户 ");
		stringBuilder.append(customer.getCustomerRealName());
		stringBuilder.append("  账号 ");
		stringBuilder.append(customer.getCustomerPhone());
		stringBuilder.append("，申请提款");
		stringBuilder.append(amountMoney);
		stringBuilder.append("元");
		try {
			SMSUtil.sendSMS(stringBuilder.toString(),customer.getCustomerPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseJson;
	}
}
