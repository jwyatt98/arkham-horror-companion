package com.wanderingwyatt.arkham.modules;

import com.wanderingwyatt.arkham.components.DatabaseComponent;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

@Module(subcomponents = DatabaseComponent.class)
public class DatabaseModule {
	@Provides
	@Singleton
	EntityManagerFactory provideEntityManagerFactory(DatabaseComponent.Builder databaseComponentBuilder) {
		return databaseComponentBuilder.databaseModule(new DatabaseImplModule()).build().entityManagerFactory();
	}
}
