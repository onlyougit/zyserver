package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.zyserver.entity.Settlement;

import java.util.List;

public interface SettlementRepository extends JpaRepository<Settlement,Integer>,JpaSpecificationExecutor<Settlement> {

	@Query("from t_settlement where to_days(create_time)=to_days(now()) and status=1")
	List<Settlement> queryTodaySettlement();
}
