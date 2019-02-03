package io.example.auxiliary.annotations;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides basic metadata for verticle deployment.
 */
@Component
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpringVerticle {
    int DEFAULT_INSTANCES = -1;

    VerticleType value() default VerticleType.EVENTLOOP;

    /**
     * If value == DEFAULT_INSTANCES, then value().instances() is used to determine number of deployments.
     * @return number of verticle instances to deploy
     */
    int instances() default DEFAULT_INSTANCES;

    enum VerticleType {
        WORKER(5), EVENTLOOP(1);
        @Getter
        final int instances;

        VerticleType(final int instances) {
            this.instances = instances;
        }

        public boolean isWorker() {
            return WORKER == this;
        }
    }
}
