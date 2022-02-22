package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Expansion;
import com.wanderingwyatt.arkham.game.components.Investigator;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.cfg.Configuration;

public abstract class DaoModule {
	
	private Set<Class<?>> persistenceClasses;

	DaoModule() {
		persistenceClasses = new HashSet<>();
		createPersistenceClassSet();
	}
	
	protected abstract Configuration getConfiguration();
	protected abstract void configureCache();
	
	public PersistenceDaoManager createPersistenceDaoManager() {
		configureCache();
		Configuration configuration = getConfiguration();
		persistenceClasses.forEach(configuration::addAnnotatedClass);
		
		return new ArkhamHorrorDao(configuration.buildSessionFactory());
	}
	
	private void createPersistenceClassSet() {
		persistenceClasses.add(Investigator.class);
		persistenceClasses.add(Expansion.class);
	}
}
