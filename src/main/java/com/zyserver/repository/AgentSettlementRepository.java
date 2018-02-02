package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.zyserver.entity.AgentSettlement;

public interface AgentSettlementRepository extends JpaRepository<AgentSettlement,Integer>,JpaSpecificationExecutor<AgentSettlement> {

}
