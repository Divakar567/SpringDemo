package com.example.library.util.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Searchable {

    @AliasFor("value")
    String topic() default "";

    @AliasFor("topic")
    String value() default "";

    String index();
}
