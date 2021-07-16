package com.wanderingwyatt.arkham.modules;

import com.wanderingwyatt.arkham.server.ServerConfig;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.aeonbits.owner.ConfigFactory;

@Module
public class ConfigurationModule {
	@Singleton
	@Provides
	ServerConfig provideServerConfig() {
		return ConfigFactory.create(ServerConfig.class);
	}
}
