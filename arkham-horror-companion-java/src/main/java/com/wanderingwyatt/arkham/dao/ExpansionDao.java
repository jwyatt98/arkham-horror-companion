package com.wanderingwyatt.arkham.dao;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import com.wanderingwyatt.arkham.game.components.Expansion;
import dagger.Lazy;

@Singleton
public class ExpansionDao extends AbstractArkhamHorrorDao<Expansion, Integer> {
	@Inject
	public ExpansionDao(Lazy<EntityManagerFactory> entityManagerFactory) {
		super(entityManagerFactory);
	}

	public Expansion findByName(String expansionName) throws ArkhamHorrorDaoException {
		return performInTransaction(entityManager -> {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Expansion> expansionQuery = criteriaBuilder.createQuery(Expansion.class);
			Root<Expansion> rootExpansion = expansionQuery.from(Expansion.class);
			rootExpansion.fetch("investigators", JoinType.INNER);
			expansionQuery.select(rootExpansion);
			expansionQuery.where(criteriaBuilder.equal(rootExpansion.get("name"), expansionName));
			Query nameQuery = entityManager.createQuery(expansionQuery);
			return (Expansion)nameQuery.getSingleResult();
		});
	}
	
	@Override
	protected void addAttributeNodes(EntityGraph<Expansion> entityGraph) {
		entityGraph.addAttributeNodes("name", "investigators");
	}
}
