package com.wanderingwyatt.arkham.dao;

import com.wanderingwyatt.arkham.modules.generated.CacheConfigurer;
import com.wanderingwyatt.arkham.server.ServerConfig;
import org.hibernate.cache.jcache.ConfigSettings;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

public class ArkhamHorrorDaoModule extends DaoModule {

	private final ServerConfig serverConfig;
	private final CacheConfigurer cacheConfigurer;

	public ArkhamHorrorDaoModule(ServerConfig serverConfig, CacheConfigurer cacheConfigurer) {
		this.serverConfig = serverConfig;
		this.cacheConfigurer = cacheConfigurer;
	}

	@Override
	protected Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.setProperty(AvailableSettings.DATASOURCE, "java:comp/env/jdbc/arkhamHorrorDb");
		configuration.setProperty(AvailableSettings.DIALECT, serverConfig.databaseDialect());
		configuration.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		configuration.setProperty(AvailableSettings.SHOW_SQL, "true");
		configuration.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "true");
		configuration.setProperty(AvailableSettings.USE_QUERY_CACHE, "true");
		configuration.setProperty(AvailableSettings.CACHE_REGION_FACTORY, "org.hibernate.cache.jcache.JCacheRegionFactory");
		configuration.setProperty(ConfigSettings.PROVIDER, "org.ehcache.jsr107.EhcacheCachingProvider");
		return configuration;
	}
	
	@Override
	protected void configureCache() {
		this.cacheConfigurer.configure();		
	}
}
