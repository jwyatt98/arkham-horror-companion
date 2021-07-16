package com.wanderingwyatt.arkham.components;

import com.wanderingwyatt.arkham.dao.ExpansionDao;
import com.wanderingwyatt.arkham.dao.InvestigatorDao;
import dagger.Lazy;
import javax.persistence.EntityManagerFactory;

public interface ApplicationComponent {
	Lazy<EntityManagerFactory> entityManagerFactory();
	InvestigatorDao investigatorDao();
	ExpansionDao expansionDao();
}
