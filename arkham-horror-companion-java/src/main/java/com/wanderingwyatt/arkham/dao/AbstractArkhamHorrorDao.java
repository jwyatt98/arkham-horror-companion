package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import dagger.Lazy;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

public class AbstractArkhamHorrorDao implements PersistenceDao {
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@Inject
	protected AbstractArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	public DaoContext createDaoContext() {
		return new DaoContext(entityManagerFactory.get().createEntityManager());
	}

	public <T extends Identifiable> void persist(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			context.persist(t);
		}
	}
	
	public <T extends Identifiable> T merge(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.merge(t);
		}
	}

	public <T extends Identifiable> void remove(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			context.remove(t);
		}
	}
	
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.find(clazz, id);
		}
	}
	
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.findByEntityGraph(type, id, egp);
		}
	}
}
