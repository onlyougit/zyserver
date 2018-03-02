package com.zyserver.repository;

import com.zyserver.entity.BankCard;
import com.zyserver.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by jinxinlong on 2017/10/09.
 */
public interface NoticeRepository extends JpaRepository<Notice,Integer>,JpaSpecificationExecutor<Notice> {

	List<Notice> findByStatusOrderByIdDesc(int i);
}
