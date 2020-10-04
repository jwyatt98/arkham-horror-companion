package com.wanderingwyatt.arkham.server;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({ "system:properties",
           "system:env" })
public interface ServerConfig extends Config {
	@Key("arkham.server.database.dialect")
	@DefaultValue("org.hibernate.dialect.H2Dialect")
	String databaseDialect();
}