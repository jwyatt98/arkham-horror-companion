package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Identifiable;
import dagger.Lazy;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;

public class ArkhamHorrorDao implements PersistenceDaoManager {
	private Lazy<EntityManagerFactory> entityManagerFactory;
	
	@Inject
	public ArkhamHorrorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
	
	@Override
	public DaoContext createDaoContext() {
		return new DaoContext(entityManagerFactory.get().createEntityManager());
	}

	@Override
	public <T extends Identifiable> void persist(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			context.persist(t);
		}
	}
	
	@Override
	public <T extends Identifiable> T merge(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.merge(t);
		}
	}

	@Override
	public <T extends Identifiable> void remove(T t) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			context.remove(t);
		}
	}
	
	@Override
	public <T extends Identifiable> Optional<T> find(Class<T> clazz, UUID id) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.find(clazz, id);
		}
	}
	
	@Override
	public <T extends Identifiable> Optional<T> findByEntityGraph(Class<T> type, UUID id, EntityGraphProvider egp) throws ArkhamHorrorDaoException {
		try (DaoContext context = createDaoContext()) {
			return context.findByEntityGraph(type, id, egp);
		}
	}
}
