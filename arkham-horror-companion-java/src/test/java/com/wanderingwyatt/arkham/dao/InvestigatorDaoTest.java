package com.wanderingwyatt.arkham.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponentTestBridge;
import com.wanderingwyatt.arkham.components.DaggerTestApplicationComponent;
import com.wanderingwyatt.arkham.game.components.Expansion;
import com.wanderingwyatt.arkham.game.components.Investigator;
import com.wanderingwyatt.arkham.game.components.SkillTrack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class InvestigatorDaoTest {
	private static final String THE_WRITER = "The Writer";
	private static final String THE_AUTHOR = "The Author";
	private static final String SCIENCE_BUILDING = "Science Building";
	private static final String VELMA_S_DINER = "Velma's Diner";
	static AbstractArkhamHorrorDao investigatorDao;
	static Investigator investigator;
	private static Expansion expansion;
	
	@BeforeAll
	static void setUp() throws Exception {
		ArkhamHorrorApplicationComponentTestBridge.setInstance(DaggerTestApplicationComponent.create());
		investigatorDao = ArkhamHorrorApplicationComponent.getInstance().arkhamDao();
		SkillTrack skillTrack = SkillTrack.builder()
			.withSpeed(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withSneak(new ArrayList<Integer>(Arrays.asList(3,2,1,0)))
			.withFight(new ArrayList<Integer>(Arrays.asList(0,1,2,3)))
			.withWill(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.withLore(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withLuck(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.build();
		
		expansion = Expansion.builder()
				.withName("Investigator Expansion 1")
				.build();
		
		investigator = Investigator.builder()
			.withExpansion(expansion)
			.withName("Investigator Test 1")
			.withTitle(THE_AUTHOR)
			.withFocus(2)
			.withHealth(4)
			.withSanity(6)
			.withHome(VELMA_S_DINER)
			.withSkillTrack(skillTrack)
			.build();
		
		expansion.addInvestigator(investigator);
	}
	
	static protected <R> R performInTransaction(Function<EntityManager, R> work) throws ArkhamHorrorDaoException {
		EntityManager entityManager = investigatorDao.getEntityManager();
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
	
	@AfterAll
	static void tearDown() throws Exception {
		performInTransaction(entityManager -> {
			investigatorDao.remove(expansion, entityManager);
			return java.lang.Void.class;
		});
	}

	@Test
	@Order(1)
	void testPersist() throws Exception {
		performInTransaction(entityManager -> {
			investigatorDao.persist(expansion, entityManager);
			return java.lang.Void.class;
		});
		assertNotNull(investigator.getId());
	}

	@Test
	@Order(2)
	void testFind() throws Exception {
		Investigator investigatorFound = 
				performInTransaction(entityManager -> {
					Optional<Investigator> didWeFindIt = investigatorDao.find(Investigator.class, investigator.getId(), entityManager);
					return didWeFindIt.get();
				});
		assertEquals(investigator, investigatorFound);
		assertEquals(investigator.hashCode(), investigatorFound.hashCode());
		assertNotSame(investigator, investigatorFound);
	}
	
	@Test
	@Order(3)
	void testFindByEntityGraph() throws Exception {
		Investigator investigatorFound = 
		performInTransaction(entityManager -> {
			Optional<Investigator> findByEntityGraph = investigatorDao.findByEntityGraph(Investigator.class, investigator.getId(), Investigator::addAttributeNodes, entityManager);
			return findByEntityGraph.get();
		});
		assertEquals(investigator, investigatorFound);
		assertEquals(investigator.hashCode(), investigatorFound.hashCode());
		assertNotSame(investigator, investigatorFound);
	}
	
	@Test
	@Order(4)
	void testMerge() throws Exception {
		investigator.setHealth(10);
		Investigator mergedInvestigator = performInTransaction(entityManager -> {
			return investigatorDao.merge(investigator, entityManager);
		});
		
		assertEquals(investigator, mergedInvestigator);
		assertNotSame(investigator, mergedInvestigator);
		assertEquals(10, mergedInvestigator.getHealth());
	}
	
	@Test
	@Order(5)
	void testNotFound() throws Exception {
		Investigator exceptionOrNull = performInTransaction(entityManager -> {
			Optional<Investigator> investigator = investigatorDao.find(Investigator.class, UUID.randomUUID(), entityManager);
			assertFalse(investigator.isPresent());
			return null;
		});
	}
	
	@Test
	@Order(6)
	void testPersistException() throws Exception {
		assertThrows(ArkhamHorrorDaoException.class, () -> {
			performInTransaction(entityManager -> {
				investigatorDao.persist(investigator, entityManager);
				return java.lang.Void.class;
			});
		});
	}
	
	@Test
	@Order(7)
	void testBuilderPattern() {
		assertEquals(2, investigator.getFocus());
		investigator.setFocus(4);
		assertEquals(4, investigator.getFocus());
		
		assertEquals(10, investigator.getHealth());
		investigator.setHealth(3);
		assertEquals(3, investigator.getHealth());
		
		assertEquals(6, investigator.getSanity());
		investigator.setSanity(7);
		assertEquals(7, investigator.getSanity());

		assertEquals(VELMA_S_DINER, investigator.getHome());
		investigator.setHome(SCIENCE_BUILDING);
		assertEquals(SCIENCE_BUILDING, investigator.getHome());
		
		assertEquals(THE_AUTHOR, investigator.getTitle());
		investigator.setTitle(THE_WRITER);
		assertEquals(THE_WRITER, investigator.getTitle());
	}
}
