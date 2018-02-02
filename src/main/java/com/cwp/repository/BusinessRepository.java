package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cwp.entity.Business;

public interface BusinessRepository extends JpaRepository<Business,Integer>,JpaSpecificationExecutor<Business> {

}
