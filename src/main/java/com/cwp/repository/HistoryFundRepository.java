package com.cwp.repository;

import com.cwp.entity.HistoryFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoryFundRepository extends JpaRepository<HistoryFund,Integer>,JpaSpecificationExecutor<HistoryFund> {

}
