package com.sneltransport.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.sneltransport.entity.DemoEntity;

@Repository
public class DemoEntityDaoImpl implements DemoEntityDao {

	@PersistenceContext	
	private EntityManager entityManager;	
	
	@Override
	public void createArticle(DemoEntity demoEntity) {
		entityManager.persist(demoEntity);

	}

}
