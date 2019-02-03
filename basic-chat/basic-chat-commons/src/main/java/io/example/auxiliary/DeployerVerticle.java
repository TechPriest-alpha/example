package io.example.auxiliary;

import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.eventbus.BaseCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import lombok.Getter;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

public class DeployerVerticle extends AbstractVerticle {

    @Getter
    private AnnotationConfigApplicationContext ctx;

    @Override
    public void start(final Future<Void> startFuture) {
        this.ctx = new AnnotationConfigApplicationContext(System.getProperty(Constants.CFG_PACKAGE_CONFIG_NAME));
        vertx.registerVerticleFactory(new SpringVerticleFactory(ctx));
        vertx.eventBus().registerCodec(new BaseCodec());
        final List<Future> deployments = ctx.getBeansWithAnnotation(SpringVerticle.class).entrySet().stream().map(entry -> {
            val name = entry.getKey();
            val bean = entry.getValue();
            val beanConfig = bean.getClass().getAnnotation(SpringVerticle.class);
            val options = makeOptions(beanConfig);
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

    private DeploymentOptions makeOptions(final SpringVerticle beanConfig) {
        final var options = new DeploymentOptions().setConfig(config());
        //run verticles as EventLoop ones and handle blocking operations
        //with execute blocking
        options.setWorker(beanConfig.value().isWorker());
        if (beanConfig.instances() == SpringVerticle.DEFAULT_INSTANCES) {
            options.setInstances(beanConfig.value().getInstances());
        } else {
            options.setInstances(beanConfig.instances());
        }
        return options;
    }
}
