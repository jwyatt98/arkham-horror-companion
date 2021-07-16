package com.wanderingwyatt.arkham.server;

import static com.wanderingwyatt.arkham.server.ArkhamHorrorCompanionServletContextInitializer.APPLICATION_COMPONENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponentTestBridge;
import com.wanderingwyatt.arkham.components.DaggerTestApplicationComponent;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class ArkhamHorrorCompanionServletContextInitializerTest {
	@Mock static ServletContextEvent sce;
	@Mock static ServletContext sc;
	
	ArkhamHorrorCompanionServletContextInitializer contextInitializer;
	
	@BeforeAll
	static void beforeAll() {
		ArkhamHorrorApplicationComponentTestBridge.setInstance(DaggerTestApplicationComponent.create());
	}
	
	@BeforeEach
	void initForEach() {
		when(sce.getServletContext()).thenReturn(sc);
		contextInitializer = new ArkhamHorrorCompanionServletContextInitializer();
	}
	
	@Test
	@Order(1)
	void testContextInitialized() {		
		contextInitializer.contextInitialized(sce);
		verify(sc).setAttribute(APPLICATION_COMPONENT, ArkhamHorrorApplicationComponent.getInstance());
	}

	@Test
	@Order(2)
	void testContextDestroyed() {
		when(sc.getAttribute(APPLICATION_COMPONENT)).thenReturn(ArkhamHorrorApplicationComponent.getInstance());
		contextInitializer.contextDestroyed(sce);
		verify(sc).getAttribute(APPLICATION_COMPONENT);
		EntityManagerFactory entityManagerFactory = ArkhamHorrorApplicationComponent.getInstance().entityManagerFactory().get();
		assertFalse(entityManagerFactory.isOpen());
	}
}
