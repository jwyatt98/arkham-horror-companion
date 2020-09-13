package com.gilead.arkhamhorrorcompanion;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ArkhamHorrorCompanionServletContextInitializer implements ServletContextListener {
	private static final Logger LOGGER = LogManager.getLogger(ArkhamHorrorCompanionServletContextInitializer.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Initialized.");
		ServletContextListener.super.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Destroyed.");
		ServletContextListener.super.contextDestroyed(sce);
	}
}
