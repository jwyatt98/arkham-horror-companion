package com.wanderingwyatt.arkham.annotations.cache;

import static java.lang.annotation.RetentionPolicy.CLASS;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(CLASS)
@Target(ElementType.TYPE)
public @interface CacheConfiguration {
	String cacheName();
	Class<?> key();
	ExpiryPolicy expiryPolicy() default ExpiryPolicy.ETERNAL;
	TimeUnit timeUnit() default TimeUnit.MINUTES;
	long length() default 0L;
	
	public enum ExpiryPolicy {
		ETERNAL,
		ACCESSED,
	}
}
