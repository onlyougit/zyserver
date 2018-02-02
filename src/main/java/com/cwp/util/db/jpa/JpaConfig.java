package com.cwp.util.db.jpa;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * JPA Spring Boot 配置类
 * @author 陈 志斌
 * @version 0.1
 */
@EnableJpaRepositories(basePackages = {"com.cwp"})
@EntityScan("com.cwp.*")
@Configuration
public class JpaConfig {

}
