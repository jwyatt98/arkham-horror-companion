package com.wanderingwyatt.arkham.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponentTestBridge;
import com.wanderingwyatt.arkham.components.DaggerTestApplicationComponent;
import com.wanderingwyatt.arkham.game.components.Expansion;
import com.wanderingwyatt.arkham.game.components.Investigator;
import com.wanderingwyatt.arkham.game.components.SkillTrack;

@TestMethodOrder(OrderAnnotation.class)
class ExpansionDaoTest {
	private static final String TEST_EXPANSION_NAME = "TEST EXPANSION";
	private static final String SCIENCE_BUILDING = "Science Building";
	private static final String VELMA_S_DINER = "Velma's Diner";
	static ExpansionDao expansionDao;
	private static Expansion expansion;
	
	@BeforeAll
	static void setUp() throws Exception {
		ArkhamHorrorApplicationComponentTestBridge.setInstance(DaggerTestApplicationComponent.create());
		expansionDao = ArkhamHorrorApplicationComponent.getInstance().expansionDao();
		SkillTrack skillTrack = SkillTrack.builder()
			.withSpeed(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withSneak(new ArrayList<Integer>(Arrays.asList(3,2,1,0)))
			.withFight(new ArrayList<Integer>(Arrays.asList(0,1,2,3)))
			.withWill(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.withLore(new ArrayList<Integer>(Arrays.asList(1,2,3,4)))
			.withLuck(new ArrayList<Integer>(Arrays.asList(5,4,3,2)))
			.build();
		
		Investigator investigator = Investigator.builder()
				.withName("Expansion Character 1")
				.withTitle("The Author")
				.withFocus(2)
				.withHealth(4)
				.withSanity(6)
				.withHome(VELMA_S_DINER)
				.withSkillTrack(skillTrack)
				.build();
		
		expansion = Expansion.builder()
				.withName(TEST_EXPANSION_NAME)
				.withInvestigators(Arrays.asList(investigator))
				.build();
		
		investigator.setExpansion(expansion);
	}

	@AfterAll
	static void tearDown() throws Exception {
		Expansion foundExpansion = expansionDao.findByEntityGraph(expansion.getId());
		expansionDao.remove(foundExpansion);
	}
	
	@Test
	@Order(1)
	void testPersist() throws Exception {
		expansionDao.persist(expansion);
		assertEquals(1, expansion.getInvestigators().size());
		Investigator investigator = expansion.getInvestigators().get(0);
		assertNotNull(investigator.getId());
	}
	
	@Test
	@Order(2)
	void testFindByName() throws Exception {
		Expansion baseGame = expansionDao.findByName(TEST_EXPANSION_NAME);
		assertEquals(1, baseGame.getInvestigators().size());
		assertEquals(TEST_EXPANSION_NAME, baseGame.getName());
	}
	
	@Test
	@Order(3)
	void testExpansionUpdate() throws Exception {
		Expansion baseGame = expansionDao.findByName(TEST_EXPANSION_NAME);
		
		SkillTrack kateSkillTrack = SkillTrack.builder()
			.withFight(Arrays.asList(1,2,3,4))
			.withLore(Arrays.asList(2,3,4,5))
			.withLuck(Arrays.asList(4,3,2,1))
			.withSneak(Arrays.asList(5,4,3,2))
			.withSpeed(Arrays.asList(1,2,3,4))
			.withWill(Arrays.asList(3,2,1,0)).build();
		
		Investigator kateInvestigator = Investigator.builder()
			.withName("Expansion Character 2")
			.withHealth(4)
			.withSanity(6)
			.withHome(SCIENCE_BUILDING)
			.withFocus(1)
			.withSkillTrack(kateSkillTrack)
			.withTitle("The Scientist")
			.withExpansion(baseGame).build();
		
		baseGame.addInvestigator(kateInvestigator);
		expansion = expansionDao.merge(baseGame);
		
		Expansion baseGameSecondTime = expansionDao.findByName(TEST_EXPANSION_NAME);
		assertEquals(2, baseGameSecondTime.getInvestigators().size());
	}
	
	@Test
	@Order(4)
	void testRemoveInvestigator() throws Exception {
		Expansion baseGame = expansionDao.findByName(TEST_EXPANSION_NAME);
		
		Optional<Investigator> gloriaOptional = baseGame.getInvestigators().stream().filter(investigator -> "Expansion Character 1".equals(investigator.getName())).findFirst();
		
		if(gloriaOptional.isPresent()) {
			Investigator investigator = gloriaOptional.get();
			baseGame.removeInvestigator(investigator);
			assertEquals(1, baseGame.getInvestigators().size());
			expansion = expansionDao.merge(baseGame);
			Expansion baseGameUpdated = expansionDao.findByName(TEST_EXPANSION_NAME);
			assertEquals(1, baseGameUpdated.getInvestigators().size());
		} else {
			fail("Could not find Expansion Character 1 in the Base Expansion");
		}
	}
}
