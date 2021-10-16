package com.wanderingwyatt.arkham.dao;

public interface PersistenceDaoManager extends PersistenceDao {
	DaoContext createDaoContext();
}
