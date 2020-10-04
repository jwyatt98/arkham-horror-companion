package com.wanderingwyatt.arkham.components;

import javax.inject.Singleton;
import com.wanderingwyatt.arkham.modules.ConfigurationModule;
import com.wanderingwyatt.arkham.modules.DatabaseModule;
import dagger.Component;

@Component(modules = {DatabaseModule.class, ConfigurationModule.class})
@Singleton
public interface ProductionApplicationComponent extends ApplicationComponent {}
