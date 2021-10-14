package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import dagger.Lazy;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class AbstractArkhamHorrorDao {
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@Inject
	protected AbstractArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public EntityManager getEntityManager() {
		return this.entityManagerFactory.get().createEntityManager();
	}
	
	public <T extends Identifiable> void persist(T t, EntityManager em) {
		em.persist(t);
	}
	
	public <T extends Identifiable> T merge(T t, EntityManager em) {
		return em.merge(t);
	}
	
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id, EntityManager em) {
		return Optional.ofNullable(em.find(clazz, id));
	}
	
	public <T extends Identifiable> void remove(T t, EntityManager em) {
		Optional<? extends Identifiable> found = Optional.ofNullable(em.find(t.getClass(), t.getId()));
		if(found.isPresent()) {
			em.remove(found.get());
		}
	}
	
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp,  EntityManager em) {
		EntityManager entityManager = entityManagerFactory.get().createEntityManager();
		EntityGraph<T> entityGraph = entityManager.createEntityGraph(type);
		egp.addAttributeNodes(entityGraph);
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return Optional.ofNullable(entityManager.find(type, id, properties));
	}
	
	@FunctionalInterface
	public static interface EntityGraphProvider {
		void addAttributeNodes(EntityGraph<?> entityGraph);
	}
}
