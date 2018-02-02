package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cwp.entity.Customer;

/**
 * Created by Yanly on 2017/8/26.
 */
public interface CustomerRepository extends JpaRepository<Customer,Integer> ,JpaSpecificationExecutor<Customer> {


	Customer findBycustomerName(String customerName);

	Customer findByCustomerPhoneAndStatus(String customerName,Integer customerStatus);

	Customer findByIdAndCustomerPassword(Integer customerId,
			String prePassword);
	
	Customer findByCustomerCardId(String customerCardId);
	Customer findByIdAndStatus(Integer customerId,Integer customerStatus);

	@Modifying
	@Transactional(readOnly = false)
	@Query("update t_customer set customer_password = :nowPassword where id = :customerId")
	void updatePassword(@Param("customerId")Integer customerId, @Param("nowPassword")String nowPassword);


	Customer findByCustomerPhone(String userPhone);

	@Modifying
	@Transactional(readOnly = false)
	@Query("update t_customer set CUSTOMER_REAL_NAME = :customerRealName,CUSTOMER_CARD_ID = :customerCardId where id = :customerId")
	void updateCustomerRealNameAndCustomerCardId(@Param("customerId")Integer customerId,
			@Param("customerCardId")String customerCardId, @Param("customerRealName")String customerRealName);

	Customer findById(Integer customerId);

}
