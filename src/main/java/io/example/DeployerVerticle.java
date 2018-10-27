package io.example;

import io.example.cfg.SpringVerticleFactory;
import io.example.infrastructure.DtoCodec;
import io.example.infrastructure.SpringVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import lombok.Getter;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class DeployerVerticle extends AbstractVerticle {

    @Getter
    private AnnotationConfigApplicationContext ctx;

    @Override
    public void start(final Future<Void> startFuture) {
        this.ctx = new AnnotationConfigApplicationContext(System.getProperty(Constants.CFG_PACKAGE));
        vertx.registerVerticleFactory(new SpringVerticleFactory(ctx));
        vertx.eventBus().registerCodec(new DtoCodec());
        final List<Future> deployments = ctx.getBeansWithAnnotation(SpringVerticle.class).entrySet().stream().map(entry -> {
            val name = entry.getKey();
            val bean = entry.getValue();
            val beanConfig = bean.getClass().getAnnotation(SpringVerticle.class);
            val options = new DeploymentOptions().setWorker(beanConfig.worker());
            if (options.isWorker()) {
                options.setMultiThreaded(config().getBoolean(Constants.MT_OVERRIDE));
            }
            if (config().getBoolean(Constants.INSTANCES_OVERRIDE)) {
                options.setInstances(20);
            } else {
                options.setInstances(beanConfig.instances());
            }
            val f = Future.future();
            vertx.deployVerticle("spring:" + name, options, onComplete -> {
                if (onComplete.succeeded()) f.complete();
                else f.fail(onComplete.cause());
            });
            return f;
        }).collect(Collectors.toList());
        CompositeFuture.all(deployments).setHandler(complete -> {
            if (complete.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(complete.cause());
            }
        });

    }
}
