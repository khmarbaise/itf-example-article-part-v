package com.soebes.itf.maven.plugin.failure;

import com.soebes.itf.jupiter.extension.SystemProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SystemProperty(value = "executionException", content = "true")
@SystemProperty(value = "exception", content = "This is the value of exception.")
public @interface ExecutionException {
}
