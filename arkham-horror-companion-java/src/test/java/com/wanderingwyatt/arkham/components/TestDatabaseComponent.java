package com.wanderingwyatt.arkham.components;

import org.hibernate.SessionFactory;
import com.wanderingwyatt.arkham.modules.TestDatabaseImplModule;
import com.wanderingwyatt.arkham.modules.HibernateBaseModule;
import dagger.Subcomponent;

@Subcomponent(modules = {TestDatabaseImplModule.class, HibernateBaseModule.class})
public interface TestDatabaseComponent {
	SessionFactory entityManagerFactory();
	
	@Subcomponent.Builder
	public interface Builder {
		Builder databaseModule(TestDatabaseImplModule testDatabaseImplModule);
		TestDatabaseComponent build();
	}
}
