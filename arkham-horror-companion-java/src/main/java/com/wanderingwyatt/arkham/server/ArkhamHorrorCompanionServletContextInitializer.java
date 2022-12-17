package com.wanderingwyatt.arkham.server;

import com.wanderingwyatt.arkham.dao.ArkhamHorrorDaoModule;
import com.wanderingwyatt.arkham.dao.PersistenceDaoManager;
import com.wanderingwyatt.arkham.modules.generated.CacheConfigurer;
import jakarta.persistence.EntityManagerFactory;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.aeonbits.owner.ConfigFactory;


@WebListener
public class ArkhamHorrorCompanionServletContextInitializer implements ServletContextListener {
	public static final String APPLICATION_COMPONENT = "applicationComponent";
	private static final Logger LOGGER = Logger.getLogger(ArkhamHorrorCompanionServletContextInitializer.class.getName());
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Initialized.");
		
		// Get configuration
		ServerConfig serverConfig = ConfigFactory.create(ServerConfig.class);
		sce.getServletContext().setAttribute(ServerConfig.CONTEXT_ATTRIBUTE, serverConfig);
		
		ArkhamHorrorDaoModule arkhamHorrorDaoModule = new ArkhamHorrorDaoModule(serverConfig, new CacheConfigurer());
		sce.getServletContext().setAttribute(PersistenceDaoManager.CONTEXT_ATTRIBUTE, arkhamHorrorDaoModule.createPersistenceDaoManager());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info(() -> "Context Destroyed.");
		
		PersistenceDaoManager daoManager = (PersistenceDaoManager) sce.getServletContext().getAttribute(PersistenceDaoManager.CONTEXT_ATTRIBUTE);
		EntityManagerFactory entityManagerFactory = daoManager.getEntityManagerFactory();
		if(entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}
}
