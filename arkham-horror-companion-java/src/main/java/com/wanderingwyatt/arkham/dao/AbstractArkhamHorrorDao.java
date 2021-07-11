package com.wanderingwyatt.arkham.dao;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import com.wanderingwyatt.arkham.game.components.Identifiable;
import dagger.Lazy;

public abstract class AbstractArkhamHorrorDao<T extends Identifiable<K>, K> {
	private Class<T> reference;
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@SuppressWarnings("unchecked")
	protected AbstractArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
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
	
	public Class<Void> remove(T t) throws ArkhamHorrorDaoException {
		return performInTransaction(entityManager -> {
			Optional<T> foundEntity = Optional.ofNullable(entityManager.find(reference, t.getId()));
			if(foundEntity.isPresent()) {
				entityManager.remove(foundEntity.get());
			}
			return Void.TYPE;
		});
	}
	
	public T findByEntityGraph(K id) {
		EntityManager entityManager = entityManagerFactory.get().createEntityManager();
		EntityGraph<T> entityGraph = entityManager.createEntityGraph(this.reference);
		addAttributeNodes(entityGraph);
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return entityManager.find(this.reference, id, properties);
	}
	
	protected <R> R performInTransaction(Function<EntityManager, R> work) throws ArkhamHorrorDaoException {
		EntityManager entityManager = entityManagerFactory.get().createEntityManager();
		try {
			entityManager.getTransaction().begin();
			var result = work.apply(entityManager);
			entityManager.getTransaction().commit();
			return result;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to interact with the database", e);
		} finally {
			if (entityManager.isOpen()) entityManager.close();
		}
	}
	
	protected abstract void addAttributeNodes(EntityGraph<T> entityGraph);
}
