package com.lihenggen.sentinel.anno;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 自定义限流注解
 *
 * @Author: lihenggen
 * @Date: 2021-6-21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@Documented
public @interface FlowLimit {
    int flowQps() default 2000;
}

