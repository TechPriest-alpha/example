package io.example.infrastructure;

import org.springframework.context.annotation.Scope;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpringVerticle {
    int DEFAULT_VERTICLE_INSTANCES = 1;

    boolean worker() default false;
    int instances() default DEFAULT_VERTICLE_INSTANCES;
}
