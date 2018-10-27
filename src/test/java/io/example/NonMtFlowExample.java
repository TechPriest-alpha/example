package io.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Viktor
 * @since 2018-10-27
 */
class NonMtFlowExample {

    private Vertx vertx;

    @BeforeAll
    void before() {
        System.setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory");
        this.vertx = Vertx.vertx();
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
        vertx.deployVerticle(deployerVerticle);
        Thread.sleep(500L);
        runWorkload();
        vertx.undeploy(deployerVerticle.deploymentID());
    }

    private void runWorkload() {

    }
}
