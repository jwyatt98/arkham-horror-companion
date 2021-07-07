package com.wanderingwyatt.arkham.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import com.wanderingwyatt.arkham.game.components.Expansion;
import dagger.Lazy;

@Singleton
public class ExpansionDao extends AbstractArkhamHorrorDao<Expansion, Integer> {
	@Inject
	public ExpansionDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		super(entityManagerFactory);
	}
	
	@Override
	protected void addAttributeNodes(EntityGraph<Expansion> entityGraph) {
		entityGraph.addAttributeNodes("name", "investigators");
		
	}
}
