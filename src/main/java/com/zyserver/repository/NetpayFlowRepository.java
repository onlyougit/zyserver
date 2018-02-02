package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zyserver.entity.NetpayFlow;

public interface NetpayFlowRepository extends JpaRepository<NetpayFlow,Integer>,JpaSpecificationExecutor<NetpayFlow> {

	NetpayFlow findByOrderId(String out_trade_no);

	NetpayFlow findByTransactionId(String out_trade_no);


}
