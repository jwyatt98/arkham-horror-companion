package com.wanderingwyatt.arkham.components;

import javax.persistence.EntityManagerFactory;
import com.wanderingwyatt.arkham.dao.ExpansionDao;
import com.wanderingwyatt.arkham.dao.InvestigatorDao;
import dagger.Lazy;

public interface ApplicationComponent {
	Lazy<EntityManagerFactory> entityManagerFactory();
	InvestigatorDao investigatorDao();
	ExpansionDao expansionDao();
}
