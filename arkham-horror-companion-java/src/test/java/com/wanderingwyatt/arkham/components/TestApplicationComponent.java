package com.wanderingwyatt.arkham.components;

import javax.inject.Singleton;
import com.wanderingwyatt.arkham.modules.TestDatabaseModule;
import dagger.Component;

@Component(modules = TestDatabaseModule.class)
@Singleton
public interface TestApplicationComponent extends ApplicationComponent {}