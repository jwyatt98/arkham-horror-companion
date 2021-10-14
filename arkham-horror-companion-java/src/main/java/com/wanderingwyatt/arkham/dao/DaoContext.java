package com.wanderingwyatt.arkham.dao;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DaoContext implements AutoCloseable {

	private final EntityManager entityManager;
	private AtomicBoolean transactionOpen;
	private AtomicBoolean rolledback;
	private AtomicBoolean closed;

	DaoContext(EntityManager entityManager) {
		this.entityManager = entityManager;
		transactionOpen = new AtomicBoolean(false);
		rolledback = new AtomicBoolean(false);
		closed = new AtomicBoolean(false);
	}
	
	public void startTransaction() {
		if(transactionOpen.compareAndSet(false, true)) {
			this.entityManager.getTransaction().begin();			
		}
	}
	
	public void rollback() {
		if(transactionOpen.compareAndSet(true, false) && rolledback.compareAndSet(false, true) && closed.compareAndSet(false, true)) {
			this.entityManager.getTransaction().rollback();
			this.entityManager.close();
		}
	}
	
	@Override
	public void close() throws ArkhamHorrorDaoException {
		if(transactionOpen.compareAndSet(true, false) && !rolledback.get() && closed.compareAndSet(false, true)) {
			this.entityManager.getTransaction().commit();
			this.entityManager.close();
		}
	}
	
	public CriteriaBuilder createCriteriaBuilder() {
		return this.entityManager.getCriteriaBuilder();
	}
	
	public Query createQuery(CriteriaQuery<?> criteriaQuery) {
		return this.entityManager.createQuery(criteriaQuery);
	}
	
	EntityManager getEntityManager() {
		return this.entityManager;
	}
}
