package io.example.cfg;

import io.example.infrastructure.BaseVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@RequiredArgsConstructor
public class SpringVerticleFactory implements VerticleFactory {
    private final ApplicationContext ctx;

    @Override
    public String prefix() {
        return "spring";
    }

    @Override
    public Verticle createVerticle(final String verticleName, final ClassLoader classLoader) throws Exception {
        return ctx.getBean(verticleName.replaceAll(prefix() + ":", ""), BaseVerticle.class);
    }
}
