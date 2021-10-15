package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class DaoContext implements AutoCloseable, PersistenceDao {
	private final EntityManager entityManager;

	DaoContext(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.entityManager.getTransaction().begin();
	}
	
	public void rollback() {
		if(this.entityManager.isOpen() && this.entityManager.getTransaction().isActive()) {
			this.entityManager.getTransaction().rollback();
		}
	}
	
	@Override
	public void close() throws ArkhamHorrorDaoException {
		try {
			if(this.entityManager.isOpen() && !this.entityManager.getTransaction().getRollbackOnly()) { 
				this.entityManager.getTransaction().commit();
			}
		} finally {
			if(this.entityManager.isOpen()) {
				this.entityManager.close();
			}
		}
	}
	
	@Override
	public <T extends Identifiable> void persist(T t) throws ArkhamHorrorDaoException {
		try {
			this.entityManager.persist(t);
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to persist entity.", e);
		}
	}
	
	@Override
	public <T extends Identifiable> T merge(T t) throws ArkhamHorrorDaoException {
		try {			
			return this.entityManager.merge(t);
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to merge entity.", e);
		}
	}
	
	@Override
	public <T extends Identifiable> void remove(T t) throws ArkhamHorrorDaoException {
		try {
			Optional<? extends Identifiable> found = Optional.ofNullable(this.entityManager.find(t.getClass(), t.getId()));
			if(found.isPresent()) {
				this.entityManager.remove(found.get());
			}
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to remove entity.", e);
		}
	}
	
	@Override
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id) throws ArkhamHorrorDaoException {
		try {			
			return Optional.ofNullable(this.entityManager.find(clazz, id));
		} catch (Exception e) {
			this.entityManager.getTransaction().rollback();
			throw new ArkhamHorrorDaoException("Error while trying to find the entity.", e);
		}
	}
	
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp) throws ArkhamHorrorDaoException {
		try {
			EntityGraph<T> entityGraph = this.entityManager.createEntityGraph(type);
			egp.addAttributeNodes(entityGraph);
			Map<String, Object> properties = new HashMap<>();
			properties.put(JAVAX_PERSISTENCE_FETCHGRAPH, entityGraph);
			return Optional.ofNullable(this.entityManager.find(type, id, properties));
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException("Error while trying to findByEntityGraph.", e);
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
