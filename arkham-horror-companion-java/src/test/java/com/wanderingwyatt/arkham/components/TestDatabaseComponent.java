package com.wanderingwyatt.arkham.components;

import com.wanderingwyatt.arkham.modules.HibernateBaseModule;
import com.wanderingwyatt.arkham.modules.TestDatabaseImplModule;
import dagger.Subcomponent;
import org.hibernate.SessionFactory;

@Subcomponent(modules = {TestDatabaseImplModule.class, HibernateBaseModule.class})
public interface TestDatabaseComponent {
	SessionFactory entityManagerFactory();
	
	@Subcomponent.Builder
	public interface Builder {
		Builder databaseModule(TestDatabaseImplModule testDatabaseImplModule);
		TestDatabaseComponent build();
	}
}
