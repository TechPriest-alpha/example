package io.example;

import io.example.api.CommonApi;
import io.example.dto.AccessCheckRequest;
import io.example.dto.PrivateData;
import io.example.dto.PrivateDataRequest;
import io.example.dto.PublicDataRequest;
import io.example.test.fakes.Sleeper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Viktor
 * @since 2018-10-27
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NonMtFlowExample implements Sleeper {

    private Vertx vertx;

    @BeforeAll
    void before() {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        val opts = new VertxOptions().setEventLoopPoolSize(10).setWorkerPoolSize(50);
        this.vertx = Vertx.vertx(opts);
        System.setProperty(Constants.CFG_PACKAGE, "io.example.test.cfg");

    }

    @AfterAll
    void tearDown() {
        vertx.close();
    }


    @Test
    void nonMtWorkers() throws InterruptedException {
        val cfg = new JsonObject();
        cfg.put(Constants.MT_OVERRIDE, false);
        cfg.put(Constants.INSTANCES_OVERRIDE, false);
        val options = new DeploymentOptions().setConfig(cfg);
        val deployerVerticle = new DeployerVerticle();
        val pool = Executors.newFixedThreadPool(30);

        val result = new AtomicBoolean(false);
        vertx.deployVerticle(deployerVerticle, options, onComplete -> {
            result.set(true);
        });
        while (!result.get()) ;
        runWorkload(deployerVerticle.getCtx(), pool);
        vertx.undeploy(deployerVerticle.deploymentID());
        pool.shutdown();
        pool.awaitTermination(2000, TimeUnit.SECONDS);
    }

    private void runWorkload(final AnnotationConfigApplicationContext ctx, final ExecutorService pool) throws InterruptedException {
        val api = ctx.getBean(CommonApi.class);
        val start = Instant.now();

        val tasks = IntStream.range(0, 20)
            .mapToObj(i -> (Runnable) () -> {
                api.checkPrivateAccess(new AccessCheckRequest());
                api.insertPrivateData(new PrivateData());
                api.readPublicData(new PublicDataRequest());
                api.readPrivateData(new PrivateDataRequest());
                delay();
                log.info("Task done {}", i);
            })
            .map(pool::submit)
            .collect(Collectors.toList());

        tasks.forEach(task -> {
            try {
                log.info("Result: {}", task.get());
            } catch (final Exception ex) {
                log.error("Error while waiting", ex);
            }
        });
        log.info("Execution took: {}", Duration.between(start, Instant.now()));

    }
}
