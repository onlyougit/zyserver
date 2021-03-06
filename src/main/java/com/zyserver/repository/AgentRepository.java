package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.zyserver.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent,Integer>,JpaSpecificationExecutor<Agent> {

    Agent findByLoginId(Integer userId);
}
