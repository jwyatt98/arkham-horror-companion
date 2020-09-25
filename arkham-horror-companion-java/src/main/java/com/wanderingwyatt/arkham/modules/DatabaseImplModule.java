package com.wanderingwyatt.arkham.modules;

import java.util.Set;
import javax.inject.Named;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseImplModule {
	@Provides
	SessionFactory createEntityManagerFactor(@Named("configuration") Configuration configuration) {
		return configuration.buildSessionFactory();
	}
	
	@Provides
	@Named("configuration")
	Configuration provideConfiguration(@Named("annotatedPersistenceClasses") Set<Class<?>> annotatedPersistenceClasses) {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.datasource", "java:comp/env/jdbc/arkhamHorrorDb");
		configuration.setProperty("dialect", "org.hibernate.dialect.H2");
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		configuration.setProperty("hibernate.current_session_context_class", "thread");
		configuration.setProperty("show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "10");
	    
		// Add all the annotatedPersistenceClasses
		annotatedPersistenceClasses.forEach(configuration::addAnnotatedClass);
		return configuration;
	}
}
