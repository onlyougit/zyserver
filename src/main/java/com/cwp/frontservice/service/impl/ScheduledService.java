package com.cwp.frontservice.service.impl;

import com.cwp.entity.*;
import com.cwp.enums.SettlementStatus;
import com.cwp.frontservice.pojo.Result;
import com.cwp.frontservice.service.IScheduledService;
import com.cwp.repository.*;
import com.cwp.util.common.DateUtil;
import com.cwp.util.common.HttpsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduledService implements IScheduledService {

    public static final Logger log = LoggerFactory.getLogger(ScheduledService.class);
    public static final String DEFAULT_ZERO = "0";
    public static final int DEFAULT_INT_ZERO = 0;
    @Value("${https.param.sa}")
    private String sa;
    @Value("${https.param.sapass}")
    private String sapass;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private HistoryFundRepository historyFundRepository;
    @Autowired
    private SettlementRepository settlementRepository;
    @Autowired
    private AgentSettlementRepository agentSettlementRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void historyTask() {
        if (DateUtil.isHoliday()) {
            log.info("历史资金任务开始*********");
            //添加历史资金记录
            List<Fund> fundList = fundRepository.findAll();
            insertHistoryFunds(fundList);
            log.info("历史资金任务结束---------");
        }
    }

    @Transactional(rollbackFor = Exception.class)
	@Override
	public void settlementTask() {
        //判断一下是不是周末
        if(!DateUtil.isWeekend()){
        	log.info("客户结算任务开始*********");
            List<Customer> customerList = customerRepository.findAll();
            List<Settlement> settlements = new ArrayList<>();
            customerList.forEach(w->{
                Agent agent = agentRepository.findOne(w.getAgentId());
                Result result = HttpsUtil.queryaccount(w.getCustomerName(),sa,sapass);
                log.info("查询资金返回码："+result.getError().getCode()+";返回消息："+result.getError().getMessage());
                if(!"0".equals(result.getError().getCode())){
                    Settlement settlement = new Settlement();
                    settlement.setCustomerId(w.getId());
                    settlement.setAgentId(w.getAgentId());
                    settlement.setCreateTime(new Date());
                    settlement.setDailyTradeNumber(DEFAULT_INT_ZERO);
                    settlement.setSumDailyServiceCharge(new BigDecimal(DEFAULT_ZERO));
                    settlement.setSumDailyServiceChargeCost(new BigDecimal(DEFAULT_ZERO));
                    settlement.setDailyRebate(new BigDecimal(DEFAULT_ZERO));
                    settlement.setStatus(SettlementStatus.INVALID.getCode());
                    settlements.add(settlement);
                }else{
                    BigDecimal commission = result.getSummary().getCommission();
                    Integer dailyTradeNumber = commission.divide(agent.getServiceChargeStandard(),2,BigDecimal.ROUND_HALF_DOWN).intValue();
                    BigDecimal sumDailyServiceChargeCost = agent.getServiceChargeCost().multiply(new BigDecimal(dailyTradeNumber)).setScale(2,BigDecimal.ROUND_HALF_UP);
                    BigDecimal dailyRebate = commission.subtract(sumDailyServiceChargeCost).setScale(2,BigDecimal.ROUND_HALF_UP);
                    Settlement settlement = new Settlement();
                    settlement.setCustomerId(w.getId());
                    settlement.setAgentId(w.getAgentId());
                    settlement.setCreateTime(new Date());
                    settlement.setDailyTradeNumber(dailyTradeNumber);
                    settlement.setSumDailyServiceCharge(commission);
                    settlement.setSumDailyServiceChargeCost(sumDailyServiceChargeCost);
                    settlement.setDailyRebate(dailyRebate);
                    settlement.setStatus(SettlementStatus.EFFECTIVE.getCode());
                    settlements.add(settlement);
                }
                settlementRepository.save(settlements);
            });
        	log.info("客户结算任务结束*********");
        }
	}

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void agentSettlementTask() {
        //判断一下是不是周末
        if(!DateUtil.isWeekend()){
        	log.info("代理结算任务开始*********");
        	//查询今天的结算
            List<Settlement> settlements = settlementRepository.queryTodaySettlement();
            if(settlements.size()>0){
                List<AgentSettlement> agentSettlements = new ArrayList<>();
                Map<Integer, List<Settlement>> map = settlements
    					.stream()
    					.collect(Collectors.groupingBy(Settlement::getAgentId));
    			Iterator<Map.Entry<Integer, List<Settlement>>> entries = map.entrySet().iterator();
    			while (entries.hasNext()) {
    				AgentSettlement agentSettlement = new AgentSettlement();
    				Map.Entry<Integer, List<Settlement>> entry = entries.next();
    				List<Settlement> list = entry.getValue();
    				double sumDailyServiceCharge = list.stream()
    						.map(w -> w.getSumDailyServiceCharge())
    						.mapToDouble(s -> s.doubleValue()).sum();
    				double sumDailyServiceChargeCost = list.stream()
    						.map(w -> w.getSumDailyServiceChargeCost())
    						.mapToDouble(s -> s.doubleValue()).sum();
    				Integer dailyTradeNumber = list.stream()
    						.map(w -> w.getDailyTradeNumber())
    						.mapToInt(s -> s).sum();
    				BigDecimal dailyRebate = new BigDecimal(sumDailyServiceCharge).subtract(new BigDecimal(sumDailyServiceChargeCost)).setScale(2,BigDecimal.ROUND_HALF_UP);
    				agentSettlement.setAgentId(entry.getKey());
    				agentSettlement.setCreateTime(new Date());
    				agentSettlement.setSumDailyServiceCharge(new BigDecimal(sumDailyServiceCharge));
    				agentSettlement.setSumDailyServiceChargeCost(new BigDecimal(sumDailyServiceChargeCost));
    				agentSettlement.setDailyRebate(dailyRebate);
    				agentSettlement.setDailyTradeNumber(dailyTradeNumber);
    				agentSettlements.add(agentSettlement);
    			}
                agentSettlements.forEach(w->{
                    w.setCreateTime(new Date());
                    w.setDailyRebate(w.getSumDailyServiceCharge().subtract(w.getSumDailyServiceChargeCost()).setScale(2,BigDecimal.ROUND_HALF_UP));
                });
                if(agentSettlements.size()>0){
                    agentSettlementRepository.save(agentSettlements);
                }
            }
        	log.info("代理结算任务结束*********");
        }
    }

    /**
     * 添加历史资金
     * @param fundList
     */
    public void insertHistoryFunds(List<Fund> fundList){
        List<HistoryFund> historyFunds = new ArrayList<>();
        if(null != fundList && fundList.size()>0){
            fundList.forEach(w->{
                HistoryFund historyFund = new HistoryFund();
                historyFund.setFundId(w.getId());
                historyFund.setBalance(w.getBalance());
                historyFund.setDepositBalance(w.getDepositBalance());
                historyFund.setCustomerId(w.getCustomerId());
                historyFund.setRemark(w.getRemark());
                historyFund.setApplyAmount(w.getApplyAmount());
                historyFund.setInvestAmount(w.getInvestAmount());
                historyFund.setCreateTime(new Date());
                historyFund.setFlushTime(w.getFlushTime());
                historyFunds.add(historyFund);
            });
            historyFundRepository.save(historyFunds);
        }
    }
}
