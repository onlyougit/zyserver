package com.zyserver.repository;

import java.util.Date;

import com.zyserver.entity.VerifyCodeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerifyCodeRepository extends JpaRepository<VerifyCodeEntity,Integer>,JpaSpecificationExecutor<VerifyCodeEntity> {

	@Query("from t_verify_code where id=(SELECT max(id) FROM t_verify_code where mobile= :customerPhone and code= :code and status= :ordinal and overdue_time>= :date)")
	VerifyCodeEntity queryByMobileAndCodeAndStatus(@Param("customerPhone")String customerPhone,
			@Param("code")String code, @Param("ordinal")int ordinal ,@Param("date") Date date);

	@Query("SELECT count(*) from t_verify_code where mobile= :mobile and TO_DAYS(overdue_time)=TO_DAYS(now())")
	int queryCountByMobile(@Param("mobile")String mobile);

	@Query("from t_verify_code where id=(SELECT max(id) FROM t_verify_code where mobile= :customerPhone)")
	VerifyCodeEntity queryByMobile(@Param("customerPhone")String customerPhone);
}
