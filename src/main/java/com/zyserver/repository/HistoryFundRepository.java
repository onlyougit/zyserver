package com.zyserver.repository;

import com.zyserver.entity.HistoryFund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoryFundRepository extends JpaRepository<HistoryFund,Integer>,JpaSpecificationExecutor<HistoryFund> {

}
