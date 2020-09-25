package com.wanderingwyatt.arkham.modules;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import com.wanderingwyatt.arkham.components.DatabaseComponent;
import dagger.Module;
import dagger.Provides;

@Module(subcomponents = DatabaseComponent.class)
public class DatabaseModule {
	@Provides
	@Singleton
	EntityManagerFactory provideEntityManagerFactory(DatabaseComponent.Builder databaseComponentBuilder) {
		return databaseComponentBuilder.databaseModule(new DatabaseImplModule()).build().entityManagerFactory();
	}
}
