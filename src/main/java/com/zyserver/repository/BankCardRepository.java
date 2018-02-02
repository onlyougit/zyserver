package com.zyserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zyserver.entity.BankCard;

/**
 * Created by jinxinlong on 2017/10/09.
 */
public interface BankCardRepository extends JpaRepository<BankCard,String>,JpaSpecificationExecutor<BankCard> {

	BankCard findByBankCardId(String bankCardId);
	
	List<BankCard> findByCustomerId(Integer customerId);
}
