package io.example.auxiliary;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.errors.GeneralInternalError;
import io.example.auxiliary.errors.NoHandlerFound;
import io.example.auxiliary.eventbus.BaseDeliveryOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BaseVerticle extends AbstractVerticle {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConcurrentMap<Class<?>, MethodHandle> FOUND_HANDLERS = new ConcurrentHashMap<>();

    protected <T> void sendMessage(final String address, final T message) {
        vertx.eventBus().send(address, message, new BaseDeliveryOptions());
    }

    protected void registerConsumer(final String address) {
        vertx.eventBus().consumer(address, this::messageHandler);
    }

    /**
     * Implementation is for demo purposes only.
     * It lacks several optimizations (pre-caching of handlers, handler search in parent classes, etc.)
     * @param tMessage message to handle
     * @param <T> type of message data
     */
    private <T> void messageHandler(final Message<T> tMessage) {
        final var data = tMessage.body();
        if (data != null) {
            final var dataClass = data.getClass();
            if (FOUND_HANDLERS.containsKey(dataClass)) {
                handleMessage(data, FOUND_HANDLERS.get(dataClass));
            } else {
                final var handlerMethod = Arrays.stream(this.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(HandlerMethod.class))
                    .filter(method -> !method.isBridge())
                    .filter(method -> !method.isSynthetic())
                    .filter(method -> method.getParameterCount() == 1)
                    .filter(method -> method.getParameterTypes()[0].isAssignableFrom(dataClass))
                    .findFirst()
                    .orElseThrow(() -> new NoHandlerFound("No handler found on " + this.getClass().getSimpleName() + " for " + dataClass));
                final var handle = unreflect(handlerMethod);
                FOUND_HANDLERS.putIfAbsent(dataClass, handle);
                handleMessage(data, handle);
            }
        }
    }

    private <T> void handleMessage(final T data, final MethodHandle handle) {
        try {
            handle.invoke(this, data);
        } catch (final Throwable ex) {
            throw new GeneralInternalError(ex);
        }
    }

    private MethodHandle unreflect(final Method handlerMethod) {
        try {
            final var mhLookup = MethodHandles.lookup();
            return mhLookup.unreflect(handlerMethod);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }
}
