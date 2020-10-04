package com.wanderingwyatt.arkham.modules;

import javax.inject.Singleton;
import org.aeonbits.owner.ConfigFactory;
import com.wanderingwyatt.arkham.server.ServerConfig;
import dagger.Module;
import dagger.Provides;

@Module
public class ConfigurationModule {
	@Singleton
	@Provides
	ServerConfig provideServerConfig() {
		return ConfigFactory.create(ServerConfig.class);
	}
}
