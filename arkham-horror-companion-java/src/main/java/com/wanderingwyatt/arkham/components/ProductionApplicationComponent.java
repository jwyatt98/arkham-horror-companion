package com.wanderingwyatt.arkham.components;

import com.wanderingwyatt.arkham.modules.ConfigurationModule;
import com.wanderingwyatt.arkham.modules.DatabaseModule;
import dagger.Component;
import javax.inject.Singleton;

@Component(modules = {DatabaseModule.class, ConfigurationModule.class})
@Singleton
public interface ProductionApplicationComponent extends ApplicationComponent {}
