package it.michelepiccirillo.scribbler.annotations;

import it.michelepiccirillo.scribbler.Metamodel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModelledBy {
	Class<? extends Metamodel<?>> value();
}	
