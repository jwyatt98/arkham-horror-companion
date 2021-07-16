package com.wanderingwyatt.arkham.game.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InvestigatorTest {
	private static final String BUILDER_NAME = "Builder Name";
	private static final String TEST_NAME = "Test Name";

	@Test
	void investigatorTestNonBuilder() {
		Investigator investigator = new Investigator();
		investigator.setId(1);
		investigator.setName(TEST_NAME);
		investigator.setHealth(10);
		investigator.setSanity(5);
		
		assertEquals(1, investigator.getId());
		assertEquals(TEST_NAME, investigator.getName());
		assertEquals(10, investigator.getHealth());
		assertEquals(5, investigator.getSanity());
	}
	
	@Test
	void investigatorTestBuilder() {
		Investigator investigator = Investigator.builder()
			.withName(BUILDER_NAME)
			.withHealth(10)
			.withSanity(10).build();
		assertNull(investigator.getId());
		assertEquals(BUILDER_NAME, investigator.getName());
		assertEquals(10, investigator.getHealth());
		assertEquals(10, investigator.getSanity());
	}
	
	@Test
	void investigatorToString() {
		String toString = Investigator.builder().withName("TestName").withHealth(5).withSanity(2).build().toString();
		assertNotNull(toString);
	}
}
