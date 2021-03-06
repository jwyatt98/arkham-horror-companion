package com.wanderingwyatt.arkham.components;

import org.hibernate.SessionFactory;
import com.wanderingwyatt.arkham.modules.DatabaseImplModule;
import com.wanderingwyatt.arkham.modules.HibernateBaseModule;
import dagger.Subcomponent;

@Subcomponent(modules = {DatabaseImplModule.class, HibernateBaseModule.class})
public interface DatabaseComponent {
	SessionFactory entityManagerFactory();
	
	@Subcomponent.Builder
	public interface Builder {
		Builder databaseModule(DatabaseImplModule testDatabaseImplModule);
		DatabaseComponent build();
	}
}
