package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.game.components.Investigator;
import dagger.Lazy;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;

@Singleton
public class InvestigatorDao extends AbstractArkhamHorrorDao<Investigator, Integer> {
	@Inject
	public InvestigatorDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		super(entityManagerFactory);
	}
	
	@Override
	protected void addAttributeNodes(EntityGraph<Investigator> entityGraph) {
		entityGraph.addAttributeNodes("name", "title", "health", "sanity", "focus", "home", "skillTrack", "expansion");
	}
}
