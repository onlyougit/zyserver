package com.zyserver.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zyserver.entity.Fund;

/**
 * Created by Yanly on 2017/8/26.
 */
public interface FundRepository extends JpaRepository<Fund,String> ,JpaSpecificationExecutor<Fund> {
    Fund findByCustomerId(Integer customerId);
    
    @Modifying
    @Query("update t_fund set BALANCE = :balance where CUSTOMER_ID = :customerId")
	void updateFund(@Param("balance")BigDecimal balance,@Param("customerId")Integer customerId);

    @Modifying
    @Query("update t_fund set BALANCE = :balance,COMMISSION = :commission where CUSTOMER_ID = :customerId")
	void updateCommission(@Param("balance")BigDecimal balance,@Param("commission")BigDecimal commission,@Param("customerId")Integer customerId);
}
