package com.wanderingwyatt.arkham.modules;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import com.wanderingwyatt.game.components.Investigator;
import dagger.Module;
import dagger.Provides;

@Module
public class HibernateBaseModule {
	@Provides
	@Named("annotatedPersistenceClasses")
	Set<Class<?>> provideAnnotatedPersistenceClasses() {
		Set<Class<?>> persistenceClasses = new HashSet<>();
		persistenceClasses.add(Investigator.class);
		return persistenceClasses;
	}
}
