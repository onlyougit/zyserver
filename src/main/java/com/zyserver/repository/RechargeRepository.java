package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.zyserver.entity.Recharge;

public interface RechargeRepository extends JpaRepository<Recharge,Integer>,JpaSpecificationExecutor<Recharge> {

}
