package com.wanderingwyatt.arkham.annotations.processor;

import com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration;
import com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration.ExpiryPolicy;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@SupportedAnnotationTypes("com.wanderingwyatt.arkham.annotations.cache.CacheConfiguration")
@SupportedSourceVersion(SourceVersion.RELEASE_14)
@SupportedOptions(value = {"arkham.cache.packageName"})
public class CacheConfigurationProcessor extends BaseArkhamHorrorAnnotationProcessor {
	private static final String CACHE_CONFIGURER = ".CacheConfigurer";

	public CacheConfigurationProcessor() {
		// default constructor
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		String key = "arkham.cache.packageName";
		String packageLocation = processingEnv.getOptions().get(key);
		try {
			if(!cacheConfigurerFileExists(packageLocation + CACHE_CONFIGURER)) {
				VelocityEngine ve = new VelocityEngine();
				ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
				ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
				ve.init();
				Template cacheTemplate = ve.getTemplate("cacheconfigurer.vm");
				VelocityContext cacheContext = new VelocityContext();
				cacheContext.put("packageName", packageLocation);
				for (TypeElement annotation : annotations) {
					Set<? extends Element> cacheableElements = roundEnv.getElementsAnnotatedWith(annotation);
					Set<String> classImports = cacheableElements.stream().map(cacheableElement -> ((TypeElement) cacheableElement).getQualifiedName().toString())
						.collect(Collectors.toSet());
					Set<CacheInformation> cacheTypes = new HashSet<>();
					cacheContext.put("classImports", classImports);
					cacheableElements.forEach(cacheableElement -> {
						CacheConfiguration cacheConfigurationAnnotation = cacheableElement.getAnnotation(CacheConfiguration.class);
						CacheInformation cacheInformation = new CacheInformation();
						cacheInformation.cacheName = cacheConfigurationAnnotation.cacheName();
						cacheInformation.cacheType = cacheableElement.getSimpleName().toString();
						cacheInformation.keyType = asTypeElement(getTypeMirror((TypeElement)cacheableElement, CacheConfiguration.class)).getSimpleName().toString();
						cacheInformation.setExpiryPolicy(cacheConfigurationAnnotation.expiryPolicy());
						cacheInformation.timeUnit = cacheConfigurationAnnotation.timeUnit();
						cacheInformation.length = cacheConfigurationAnnotation.length();
						cacheTypes.add(cacheInformation);
					});
					cacheContext.put("cacheInformation", cacheTypes);
				}

				JavaFileObject cacheConfigurerJavaFile = processingEnv.getFiler().createSourceFile(packageLocation + CACHE_CONFIGURER);
				Writer writer = cacheConfigurerJavaFile.openWriter();
				cacheTemplate.merge(cacheContext, writer);
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	private boolean cacheConfigurerFileExists(String sourceFileName) {
		return processingEnv.getElementUtils().getTypeElement(sourceFileName) != null;
	}
	
	public static class CacheInformation {
		private String keyType;
		private String cacheType;
		private String cacheName;
		private ExpiryPolicy expiryPolicy;
		private TimeUnit timeUnit;
		private Long length;
		
		public String getKeyType() {
			return keyType;
		}

		public String getCacheType() {
			return cacheType;
		}

		public String getCacheName() {
			return cacheName;
		}

		public ExpiryPolicy getExpiryPolicy() {
			return expiryPolicy;
		}

		public void setExpiryPolicy(ExpiryPolicy expiryPolicy) {
			this.expiryPolicy = expiryPolicy;
		}

		public TimeUnit getTimeUnit() {
			return timeUnit;
		}

		public void setTimeUnit(TimeUnit timeUnit) {
			this.timeUnit = timeUnit;
		}

		public Long getLength() {
			return length;
		}

		public void setLength(Long length) {
			this.length = length;
		}
	}
}
