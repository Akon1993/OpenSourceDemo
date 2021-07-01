package com.zw.annodemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouwen
 * @title: TestAnnotation
 * @projectName anno-demo
 * @description:
 * @date 2021/7/110:14
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    String  value() default "";
}
