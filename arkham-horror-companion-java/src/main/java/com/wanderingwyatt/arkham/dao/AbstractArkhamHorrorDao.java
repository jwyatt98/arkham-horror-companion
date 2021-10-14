package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import dagger.Lazy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AbstractArkhamHorrorDao {
	private static final String ERROR_WHILE_TRYING_TO_FIND_THE_ENTITY = "Error while trying to find the entity.";
	private static final String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@Inject
	protected AbstractArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public DaoContext createDaoContext() {
		return new DaoContext(entityManagerFactory.get().createEntityManager());
	}
	
	public <T extends Identifiable> void persist(T t, DaoContext context) throws ArkhamHorrorDaoException {
		try {
			context.getEntityManager().persist(t);
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException("Error while trying to persist entity.", e);
		}
	}
	
	public <T extends Identifiable> void persist(T t) throws ArkhamHorrorDaoException {
		performInTransaction(entityManager -> {
			entityManager.persist(t);
			return t;
		});
	}
	
	public <T extends Identifiable> T merge(T t, DaoContext context) throws ArkhamHorrorDaoException {
		try {			
			return context.getEntityManager().merge(t);
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException("Error while trying to merge entity.", e);
		}
	}
	
	public <T extends Identifiable> T merge(T t) throws ArkhamHorrorDaoException {
		return performInTransaction(entityManager -> entityManager.merge(t));
	}
	
	public <T extends Identifiable> void remove(T t, DaoContext context) throws ArkhamHorrorDaoException {
		try {
			Optional<? extends Identifiable> found = Optional.ofNullable(context.getEntityManager().find(t.getClass(), t.getId()));
			if(found.isPresent()) {
				context.getEntityManager().remove(found.get());
			}
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException("Error while trying to remove entity.", e);
		}
	}
	
	public <T extends Identifiable> void remove(T t) throws ArkhamHorrorDaoException {
		performInTransaction(entityManager -> {
			Optional<? extends Identifiable> foundEntity =  Optional.ofNullable(entityManager.find(t.getClass(), t.getId()));
			if(foundEntity.isPresent()) {
				entityManager.remove(foundEntity.get());
			}
			return null;
		});
	}
	
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id, DaoContext context) throws ArkhamHorrorDaoException {
		try {			
			return Optional.ofNullable(context.getEntityManager().find(clazz, id));
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException(ERROR_WHILE_TRYING_TO_FIND_THE_ENTITY, e);
		}
	}
	
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id) throws ArkhamHorrorDaoException {
		try {
			EntityManager entityManager = entityManagerFactory.get().createEntityManager();
			return Optional.ofNullable(entityManager.find(clazz, id));
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException(ERROR_WHILE_TRYING_TO_FIND_THE_ENTITY, e);
		}
	}
	
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp,  EntityManager em) throws ArkhamHorrorDaoException {
		try {
			EntityGraph<T> entityGraph = em.createEntityGraph(type);
			egp.addAttributeNodes(entityGraph);
			Map<String, Object> properties = new HashMap<>();
			properties.put(JAVAX_PERSISTENCE_FETCHGRAPH, entityGraph);
			return Optional.ofNullable(em.find(type, id, properties));
		} catch (Exception e) {
			throw new ArkhamHorrorDaoException("Error while trying to findByEntityGraph.", e);
		}
	}
	
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp) {
		EntityManager entityManager = entityManagerFactory.get().createEntityManager();
		EntityGraph<T> entityGraph = entityManager.createEntityGraph(type);
		egp.addAttributeNodes(entityGraph);
		Map<String, Object> properties = new HashMap<>();
		properties.put(JAVAX_PERSISTENCE_FETCHGRAPH, entityGraph);
		return Optional.ofNullable(entityManager.find(type, id, properties));
	}
	
	private <R> R performInTransaction(Function<EntityManager, R> work) throws ArkhamHorrorDaoException {
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
	
	@FunctionalInterface
	public interface EntityGraphProvider {
		void addAttributeNodes(EntityGraph<?> entityGraph);
	}
}
