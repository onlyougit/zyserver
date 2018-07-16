package com.zyserver.core.security;

import com.zyserver.frontservice.service.IScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledConfiguration {

	@Autowired
	private IScheduledService scheduledService;

	/**
	 * 每天15：03，将持资金表中的记录 填写到历史资金表中。
	 */
	@Scheduled(cron = "0 3 15 ? * *")
	public void historyTask() {
		scheduledService.historyTask();
	}

	/**
	 * 每天15：04，结算任务。
	 */
	/*@Scheduled(cron = "0 4 15 ? * *")
	public void settlementTask() {
		scheduledService.settlementTask();
	}*/

	/**
	 * 每天15：05，代理结算任务。
	 */
	@Scheduled(cron = "0 5 15 ? * *")
	public void agentSettlementTask() {
		scheduledService.agentSettlementTask();
	}
}
