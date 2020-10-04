package com.wanderingwyatt.arkham.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponentTestBridge;
import com.wanderingwyatt.arkham.components.DaggerTestApplicationComponent;
import com.wanderingwyatt.arkham.game.components.Investigator;

@TestMethodOrder(OrderAnnotation.class)
class InvestigatorDaoTest {
	static InvestigatorDao investigatorDao;
	static Investigator investigator;
	
	@BeforeAll
	static void setUp() throws Exception {
		ArkhamHorrorApplicationComponentTestBridge.setInstance(DaggerTestApplicationComponent.create());
		investigatorDao = ArkhamHorrorApplicationComponent.getInstance().investigatorDao();
		investigator = Investigator.builder()
				.withName("Hello World")
				.withHealth(5)
				.withSanity(5).build();
	}

	@Test
	@Order(1)
	void testPersist() throws Exception {
		investigatorDao.persist(investigator);
		assertNotNull(investigator.getId());
	}

	@Test
	@Order(2)
	void testFind() throws Exception {
		Investigator investigatorFound = investigatorDao.find(investigator.getId());
		assertEquals(investigator, investigatorFound);
		assertEquals(investigator.hashCode(), investigatorFound.hashCode());
		assertNotSame(investigator, investigatorFound);
	}
	
	@Test
	@Order(3)
	void testMerge() throws Exception {
		investigator.setHealth(10);
		Investigator mergedInvestigator = investigatorDao.merge(investigator);
		assertEquals(investigator, mergedInvestigator);
		assertNotSame(investigator, mergedInvestigator);
		assertEquals(10, mergedInvestigator.getHealth());
	}
	
	@Test
	@Order(4)
	void testNotFound() throws Exception {
		Investigator exceptionOrNull = investigatorDao.find(7);
		assertNull(exceptionOrNull);
	}
	
	@Test
	@Order(5)
	void testPersistException() throws Exception {
		assertThrows(ArkhamHorrorDaoException.class, () -> {
			investigatorDao.persist(investigator);
		});
	}
}
