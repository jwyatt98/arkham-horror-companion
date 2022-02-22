package com.wanderingwyatt.arkham.dao;

import org.hibernate.cfg.Configuration;

public class TestDaoModule extends DaoModule {

	@Override
	protected void configureCache() {
		// do nothing
	}
	
	@Override
	protected Configuration getConfiguration() {
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
		return configuration;
	}
}
