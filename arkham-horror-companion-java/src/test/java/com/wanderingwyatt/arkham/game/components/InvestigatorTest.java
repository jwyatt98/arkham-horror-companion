package com.wanderingwyatt.arkham.game.components;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class InvestigatorTest {
	@Test
	void investigatorTestNonBuilder() {
		Investigator investigator = new Investigator();
		investigator.setId(1);
		investigator.setName("Test Name");
		investigator.setHealth(10);
		investigator.setSanity(5);
		
		assertEquals(1, investigator.getId());
		assertEquals("Test Name", investigator.getName());
		assertEquals(10, investigator.getHealth());
		assertEquals(5, investigator.getSanity());
	}
	
	@Test
	void investigatorTestBuilder() {
		Investigator investigator = Investigator.builder()
			.withName("Builder Name")
			.withHealth(10)
			.withSanity(10).build();
		assertNull(investigator.getId());
		assertEquals("Builder Name", investigator.getName());
		assertEquals(10, investigator.getHealth());
		assertEquals(10, investigator.getSanity());
	}
	
	@Test
	void investigatorToString() {
		String toString = Investigator.builder().withName("TestName").withHealth(5).withSanity(2).build().toString();
		assertNotNull(toString);
	}
}