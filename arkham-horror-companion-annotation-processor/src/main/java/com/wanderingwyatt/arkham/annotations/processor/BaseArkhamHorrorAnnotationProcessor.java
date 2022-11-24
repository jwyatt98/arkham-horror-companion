package com.wanderingwyatt.arkham.annotations.processor;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.AbstractProcessor;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public abstract class BaseArkhamHorrorAnnotationProcessor extends AbstractProcessor {

	public Optional<AnnotationMirror> getAnnotationMirror(final Element element, final Class<? extends Annotation> annotationClass) {
		final var annotationClassName = annotationClass.getName();
		return element.getAnnotationMirrors().stream()
				.filter( m -> m.getAnnotationType().toString().equals( annotationClassName ))
				.map(AnnotationMirror.class::cast)
				.findFirst();
	}

	public Optional<AnnotationValue> getAnnotationValue(final AnnotationMirror annotationMirror, final String name)	{
		final Elements elementUtils = this.processingEnv.getElementUtils();
		final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = elementUtils.getElementValuesWithDefaults( annotationMirror );
		return elementValues.keySet().stream()
				.filter( k -> k.getSimpleName().toString().equals(name))
				.map(elementValues::get)
				.map(AnnotationValue.class::cast)
				.findAny();
	}

	public Optional<TypeMirror> getTypeMirror(TypeElement foo, Class<? extends Annotation> annotationClass) {
		return getAnnotationMirror(foo, annotationClass)
				.flatMap(aMirror -> getAnnotationValue(aMirror, "key"))
				.map(AnnotationValue::getValue)
				.map(TypeMirror.class::cast);
	}
	
	public TypeElement asTypeElement(Optional<TypeMirror> typeMirror) {
	    Types typeUtils = this.processingEnv.getTypeUtils();
	    return TypeElement.class.cast(typeUtils.asElement(typeMirror.orElseThrow()));
	}
}
