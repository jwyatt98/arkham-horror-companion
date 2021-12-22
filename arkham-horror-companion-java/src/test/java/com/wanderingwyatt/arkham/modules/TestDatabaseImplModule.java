package com.wanderingwyatt.arkham.modules;

import dagger.Module;
import dagger.Provides;
import java.util.Set;
import javax.inject.Named;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Module
public class TestDatabaseImplModule {
	@Provides
	SessionFactory createTestEntityManagerFactor(@Named("testConfiguration") Configuration configuration) {
		return configuration.buildSessionFactory();
	}

	@Provides
	@Named("testConfiguration")
	Configuration provideTestConfiguration(@Named("annotatedPersistenceClasses") Set<Class<?>> annotatedPersistenceClasses) {
		Configuration configuration = new Configuration();
		configuration.setProperty("connection.driver_class","org.h2.Driver");
		configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:db1");                                
		configuration.setProperty("hibernate.connection.username", "sa");     
		configuration.setProperty("hibernate.connection.password", "");
		configuration.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		configuration.setProperty("hibernate.current_session_context_class", "thread");
		configuration.setProperty("show_sql", "true");
		configuration.setProperty("hibernate.connection.pool_size", "10");
		configuration.setProperty("hibernate.cache.use_second_level_cache", "false");

		// Add all the annotatedPersistenceClasses
		annotatedPersistenceClasses.forEach(configuration::addAnnotatedClass);
		return configuration;
	}
}
