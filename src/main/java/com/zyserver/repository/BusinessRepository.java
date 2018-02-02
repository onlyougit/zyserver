package com.zyserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.zyserver.entity.Business;

public interface BusinessRepository extends JpaRepository<Business,Integer>,JpaSpecificationExecutor<Business> {

}
