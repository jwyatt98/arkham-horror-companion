package com.wanderingwyatt.arkham.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import com.wanderingwyatt.arkham.game.components.Investigator;
import dagger.Lazy;

@Singleton
public class InvestigatorDao extends AbstractArkhamHorrorDao<Investigator, Integer> {
	@Inject
	public InvestigatorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		super(entityManagerFactory);
	}
}
