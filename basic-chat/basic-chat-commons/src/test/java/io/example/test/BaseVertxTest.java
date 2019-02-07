package io.example.test;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.errors.GeneralInternalError;
import io.example.auxiliary.eventbus.BaseCodec;
import io.example.auxiliary.message.ClientId;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.impl.ConcurrentHashSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseVertxTest {
    private static final long DEFAULT_SYNC_TIMEOUT_MS = 300;
    protected static Vertx vertx;
    final ConcurrentHashSet<MessageConsumer> consumers = new ConcurrentHashSet<>();
    private final BlockingQueue<Object> sync = new ArrayBlockingQueue<>(1);

    @BeforeAll
    static void initVertx() {
        vertx = Vertx.vertx();
        vertx.eventBus().registerCodec(new BaseCodec());
    }

    @AfterAll
    static void closeVertx() {
        vertx.close();
    }

    protected void deploy(final BaseVerticle verticle) {
        vertx.deployVerticle(verticle, this::toSync);
        awaitCompletion();
    }

    protected void undeploy(final String deploymentID) {
        vertx.undeploy(deploymentID, this::toSync);
        awaitCompletion();
    }

    @AfterEach
    void deregisterConsumers() {
        consumers.forEach(c -> {
            c.unregister(this::toSync);
            awaitCompletion();
        });

    }

    protected void addConsumer(final String address) {
        consumers.add(vertx.eventBus().consumer(address, msg -> toSync(msg.body())));
    }

    protected Object awaitCompletion() {
        try {
            return sync.poll(DEFAULT_SYNC_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }

    protected void toSync(final Object o) {
        try {
            sync.offer(o, DEFAULT_SYNC_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }

    AtomicInteger i = new AtomicInteger();

    protected ClientId nextClientId(final String baseId) {
        return new ClientId(baseId + i.incrementAndGet());
    }
}
