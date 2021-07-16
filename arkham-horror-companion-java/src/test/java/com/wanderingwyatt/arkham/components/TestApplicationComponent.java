package com.wanderingwyatt.arkham.components;

import com.wanderingwyatt.arkham.modules.TestDatabaseModule;
import dagger.Component;
import javax.inject.Singleton;

@Component(modules = TestDatabaseModule.class)
@Singleton
public interface TestApplicationComponent extends ApplicationComponent {}
