package io.example.auxiliary;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Verticle;
import io.vertx.core.spi.VerticleFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringVerticleFactory implements VerticleFactory {
    private final ApplicationContext ctx;

    @Override
    public String prefix() {
        return "spring";
    }

    @Override
    public Verticle createVerticle(final String verticleName, final ClassLoader classLoader) throws Exception {
        return ctx.getBean(verticleName.replaceFirst(prefix() + ":", ""), AbstractVerticle.class);
    }
}
