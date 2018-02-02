package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cwp.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent,Integer>,JpaSpecificationExecutor<Agent> {

}
