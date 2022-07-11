package com.wanderingwyatt.arkham.dao;

import jakarta.persistence.EntityManagerFactory;

public interface PersistenceDaoManager extends PersistenceDao {
	String CONTEXT_ATTRIBUTE = PersistenceDaoManager.class.getSimpleName() + ".contextAttribute";
	
	DaoContext createDaoContext();
	
	EntityManagerFactory getEntityManagerFactory();
}
