package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cwp.entity.AgentSettlement;

public interface AgentSettlementRepository extends JpaRepository<AgentSettlement,Integer>,JpaSpecificationExecutor<AgentSettlement> {

}
