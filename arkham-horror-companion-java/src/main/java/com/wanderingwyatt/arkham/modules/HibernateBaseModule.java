package com.wanderingwyatt.arkham.modules;

import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import com.wanderingwyatt.arkham.game.components.Expansion;
import com.wanderingwyatt.arkham.game.components.Investigator;
import dagger.Module;
import dagger.Provides;

@Module
public class HibernateBaseModule {
	@Provides
	@Named("annotatedPersistenceClasses")
	Set<Class<?>> provideAnnotatedPersistenceClasses() {
		Set<Class<?>> persistenceClasses = new HashSet<>();
		persistenceClasses.add(Investigator.class);
		persistenceClasses.add(Expansion.class);
		return persistenceClasses;
	}
}
