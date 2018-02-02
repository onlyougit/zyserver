package com.zyserver.util.db.jpa;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 
 * JPA Spring Boot 配置类
 * @author 陈 志斌
 * @version 0.1
 */
@EnableJpaRepositories(basePackages = {"com.zyserver"})
@EntityScan("com.zyserver.*")
@Configuration
public class JpaConfig {

}
