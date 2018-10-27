package io.example.test;

import io.example.Constants;
import io.example.DeployerVerticle;
import io.example.test.fakes.Sleeper;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public abstract class ExampleBase implements Sleeper {
    private static final int REPEATS = 10;
    private Vertx vertx;
    private ExecutorService pool;

    @BeforeAll
    static void before() {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }

    @AfterAll
    static void after() {
    }

    @BeforeEach
    void setUp() {
        val opts = new VertxOptions().setEventLoopPoolSize(10).setWorkerPoolSize(50);
        vertx = Vertx.vertx(opts);
        System.setProperty(Constants.CFG_PACKAGE, "io.example.test.cfg");
        this.pool = Executors.newFixedThreadPool(30);

    }

    @AfterEach
    void tearDown() throws InterruptedException {
        vertx.close();

        pool.shutdown();
        pool.awaitTermination(2000, TimeUnit.SECONDS);
    }


    @RepeatedTest(REPEATS)
    void nonMtWorkers() throws InterruptedException {
        val cfg = getConfig();
        cfg.put(Constants.MT_OVERRIDE, false);
        cfg.put(Constants.INSTANCES_OVERRIDE, false);
        val options = new DeploymentOptions().setConfig(cfg);
        val deployerVerticle = new DeployerVerticle();

        prepareAndRunWorkload(options, deployerVerticle, pool);
    }

    @RepeatedTest(REPEATS)
    void mtWorkers() throws InterruptedException {
        val cfg = getConfig();
        cfg.put(Constants.MT_OVERRIDE, true);
        cfg.put(Constants.INSTANCES_OVERRIDE, false);
        val options = new DeploymentOptions().setConfig(cfg);
        val deployerVerticle = new DeployerVerticle();

        prepareAndRunWorkload(options, deployerVerticle, pool);
    }

    protected JsonObject getConfig() {
        return new JsonObject().put(Constants.EXECUTE_AS_BLOCKING, false);
    }

    @RepeatedTest(REPEATS)
    void multiInstanceWorkers() throws InterruptedException {
        val cfg = getConfig();
        cfg.put(Constants.MT_OVERRIDE, false);
        cfg.put(Constants.INSTANCES_OVERRIDE, true);
        val options = new DeploymentOptions().setConfig(cfg);
        val deployerVerticle = new DeployerVerticle();

        prepareAndRunWorkload(options, deployerVerticle, pool);
    }


    private void prepareAndRunWorkload(final DeploymentOptions options, final DeployerVerticle deployerVerticle, final ExecutorService pool) throws InterruptedException {
        val result = new AtomicBoolean(false);
        vertx.deployVerticle(deployerVerticle, options, onComplete -> {
            result.set(true);
        });
        while (!result.get()) ;
        runBasicWorkload(deployerVerticle.getCtx(), pool);
        vertx.undeploy(deployerVerticle.deploymentID());

    }

    protected abstract void runBasicWorkload(final AnnotationConfigApplicationContext ctx, final ExecutorService pool) throws InterruptedException;
}
