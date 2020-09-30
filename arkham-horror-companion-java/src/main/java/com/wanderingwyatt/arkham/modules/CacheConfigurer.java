package com.wanderingwyatt.arkham.modules;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.wanderingwyatt.game.components.Investigator;

@Singleton
public class CacheConfigurer {
	
	@Inject
	public CacheConfigurer() {
		// Injectable constructor
	}
	
	public void configure() {
		// configure each cache
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cacheManager = provider.getCacheManager();
		configureInvestigatorCache(cacheManager);
	}
	
	private void configureInvestigatorCache(CacheManager cacheManager) {
		MutableConfiguration<Integer,Investigator> mutableConfiguration = new MutableConfiguration<Integer, Investigator>()
				.setTypes(Integer.class, Investigator.class)
				.setExpiryPolicyFactory(EternalExpiryPolicy.factoryOf());
		cacheManager.createCache("investigator-cache", mutableConfiguration);
	}
}
