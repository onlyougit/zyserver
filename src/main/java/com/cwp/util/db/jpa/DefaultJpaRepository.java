package com.cwp.util.db.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * 标准简单Jpa Entity实现
 * 
 * @author 靳欣龙
 * @version 0.1
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public class DefaultJpaRepository<T, ID extends Serializable>
	extends SimpleJpaRepository<T, ID> {
	
	public DefaultJpaRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
	}
	
}