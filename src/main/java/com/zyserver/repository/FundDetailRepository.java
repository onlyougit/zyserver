package com.zyserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zyserver.entity.FundDetail;

/**
 * Created by Yanly on 2017/8/26.
 */
public interface FundDetailRepository extends JpaRepository<FundDetail,Integer> ,JpaSpecificationExecutor<FundDetail> {

	List<FundDetail> findByCustomerId(Integer customerId);
}
