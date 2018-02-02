package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cwp.entity.Recharge;

public interface RechargeRepository extends JpaRepository<Recharge,Integer>,JpaSpecificationExecutor<Recharge> {

}
