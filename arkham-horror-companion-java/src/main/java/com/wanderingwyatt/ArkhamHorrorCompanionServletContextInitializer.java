package com.wanderingwyatt;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.wanderingwyatt.arkham.components.ApplicationComponent;
import com.wanderingwyatt.arkham.components.ArkhamHorrorApplicationComponent;

@WebListener
public class ArkhamHorrorCompanionServletContextInitializer implements ServletContextListener {
	public static final String APPLICATION_COMPONENT = "applicationComponent";
	private static final Logger LOGGER = LogManager.getLogger(ArkhamHorrorCompanionServletContextInitializer.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Initialized.");
		ApplicationComponent applicationComponent = ArkhamHorrorApplicationComponent.getInstance();
		sce.getServletContext().setAttribute(APPLICATION_COMPONENT, applicationComponent);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Destroyed.");
		ApplicationComponent applicationComponent = (ApplicationComponent)sce.getServletContext().getAttribute(APPLICATION_COMPONENT);
		EntityManagerFactory entityManagerFactory = applicationComponent.entityManagerFactory().get();
		if(entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
}