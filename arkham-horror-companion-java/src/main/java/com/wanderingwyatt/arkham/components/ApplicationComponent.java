package com.wanderingwyatt.arkham.components;

import javax.persistence.EntityManagerFactory;
import dagger.Lazy;

public interface ApplicationComponent {
	Lazy<EntityManagerFactory> entityManagerFactory();
}
