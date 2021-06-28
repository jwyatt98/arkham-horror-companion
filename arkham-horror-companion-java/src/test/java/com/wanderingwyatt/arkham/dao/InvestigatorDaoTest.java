package com.wanderingwyatt.arkham.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponentTestBridge;
import com.wanderingwyatt.arkham.components.DaggerTestApplicationComponent;
import com.wanderingwyatt.arkham.game.components.Investigator;
import com.wanderingwyatt.arkham.game.components.SkillTrack;

@TestMethodOrder(OrderAnnotation.class)
class InvestigatorDaoTest {
	static InvestigatorDao investigatorDao;
	static Investigator investigator;
	
	@BeforeAll
	static void setUp() throws Exception {
		ArkhamHorrorApplicationComponentTestBridge.setInstance(DaggerTestApplicationComponent.create());
		investigatorDao = ArkhamHorrorApplicationComponent.getInstance().investigatorDao();

		SkillTrack skillTrack = SkillTrack.builder()
			.withSpeed(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withSneak(new ArrayList<Integer>(Arrays.asList(3,2,1,0)))
			.withFight(new ArrayList<Integer>(Arrays.asList(0,1,2,3)))
			.withWill(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.withLore(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withLuck(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.build();
		
		investigator = Investigator.builder()
			.withExpansion("ArkhamHorror")
			.withName("Gloria Goldberg")
			.withTitle("The Author")
			.withFocus(2)
			.withHealth(4)
			.withSanity(6)
			.withHome("Velma's Diner")
			.withSkillTrack(skillTrack)
			.build();
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
