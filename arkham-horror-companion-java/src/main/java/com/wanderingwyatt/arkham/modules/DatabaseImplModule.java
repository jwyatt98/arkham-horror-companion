package com.wanderingwyatt.arkham.modules;

import java.util.Set;
import javax.inject.Named;
import org.hibernate.SessionFactory;
import org.hibernate.cache.jcache.ConfigSettings;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import com.wanderingwyatt.arkham.modules.generated.CacheConfigurer;
import com.wanderingwyatt.arkham.server.ServerConfig;
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
	Configuration provideConfiguration(@Named("annotatedPersistenceClasses") Set<Class<?>> annotatedPersistenceClasses, 
			CacheConfigurer cacheConfigurer,
			ServerConfig serverConfig) {
		
		// Configure all the caches before configuring hibernate for caching.
		cacheConfigurer.configure();
		
		Configuration configuration = new Configuration();
		configuration.setProperty(AvailableSettings.DATASOURCE, "java:comp/env/jdbc/arkhamHorrorDb");
		configuration.setProperty(AvailableSettings.DIALECT, serverConfig.databaseDialect());
		configuration.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
		configuration.setProperty(AvailableSettings.SHOW_SQL, "true");
		configuration.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, "true");
		configuration.setProperty(AvailableSettings.USE_QUERY_CACHE, "true");
		configuration.setProperty(AvailableSettings.CACHE_REGION_FACTORY, "org.hibernate.cache.jcache.JCacheRegionFactory");
		configuration.setProperty(ConfigSettings.PROVIDER, "org.ehcache.jsr107.EhcacheCachingProvider");
	    
		// Add all the annotatedPersistenceClasses
		annotatedPersistenceClasses.forEach(configuration::addAnnotatedClass);
		return configuration;
	}	
}
