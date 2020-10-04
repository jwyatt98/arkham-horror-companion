package com.wanderingwyatt.arkham.dao;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import dagger.Lazy;

public abstract class AbstractArkhamHorrorDao<T, K> {
	private Class<T> reference;
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@SuppressWarnings("unchecked")
	public AbstractArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
		this.reference = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void persist(T t) throws ArkhamHorrorDaoException {
		performInTransaction(entityManager -> {
			entityManager.persist(t);
			return t;
		});
	}
	
	public T merge(T t) throws ArkhamHorrorDaoException {
		return performInTransaction(entityManager -> entityManager.merge(t));
	}
	
	public T find(K id) throws ArkhamHorrorDaoException {
		return performInTransaction(entityManager -> entityManager.find(reference, id));
	}
	
	protected T performInTransaction(Function<EntityManager, T> work) throws ArkhamHorrorDaoException {
		EntityManager entityManager = entityManagerFactory.get().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			var result = work.apply(entityManager);
			entityManager.getTransaction().commit();
			// Detach the result if not null
			if (result != null) {entityManager.detach(result);}
			return result;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to interact with the database", e);
		} finally {
			if (entityManager.isOpen()) entityManager.close();
		}
		
	}
}
