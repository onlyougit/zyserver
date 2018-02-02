package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.cwp.entity.DrawingApply;

/**
 * Created by Yanly on 2017/8/26.
 */
public interface DrawingApplyRepository extends JpaRepository<DrawingApply,Integer>,JpaSpecificationExecutor<DrawingApply> {
}
