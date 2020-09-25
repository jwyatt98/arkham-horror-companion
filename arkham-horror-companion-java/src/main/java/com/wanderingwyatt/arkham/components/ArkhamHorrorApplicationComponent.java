package com.wanderingwyatt.arkham.components;

public final class ArkhamHorrorApplicationComponent {
	private ArkhamHorrorApplicationComponent() {/* defeat instantiation */}
	
	private static ApplicationComponent instance = DaggerProductionApplicationComponent.create();
	
	public static ApplicationComponent getInstance() {
		return instance;
	}
	
	// Package private setInstance method to allow test injection of the application component
	static void setInstance(ApplicationComponent robotComponent) {
		instance = robotComponent;
	}
}
