package com.cwp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.cwp.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User> {

}
