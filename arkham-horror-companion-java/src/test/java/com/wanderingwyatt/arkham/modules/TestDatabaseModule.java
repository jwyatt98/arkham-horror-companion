package com.wanderingwyatt.arkham.modules;

import com.wanderingwyatt.arkham.components.TestDatabaseComponent;
import com.wanderingwyatt.arkham.dao.ArkhamHorrorDao;
import com.wanderingwyatt.arkham.dao.PersistenceDaoManager;
import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

@Module(subcomponents = TestDatabaseComponent.class)
public class TestDatabaseModule {
	@Provides
	@Singleton
	EntityManagerFactory provideEntityManagerFactory(TestDatabaseComponent.Builder databaseComponentBuilder) {
		return databaseComponentBuilder.databaseModule(new TestDatabaseImplModule()).build().entityManagerFactory();
	}
	
	@Provides
	@Singleton
	PersistenceDaoManager getPersistenceDaoManager(Lazy<EntityManagerFactory> emf) {
		return new ArkhamHorrorDao(emf);
	}
}
