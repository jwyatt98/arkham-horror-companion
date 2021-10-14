package com.wanderingwyatt.arkham.components;

import com.wanderingwyatt.arkham.dao.AbstractArkhamHorrorDao;
import dagger.Lazy;
import javax.persistence.EntityManagerFactory;

public interface ApplicationComponent {
	Lazy<EntityManagerFactory> entityManagerFactory();
	AbstractArkhamHorrorDao arkhamDao();
}
