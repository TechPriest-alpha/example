package io.example.infrastructure;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public class BaseVerticle extends AbstractVerticle {

    private final String simpleClassName = this.getClass().getSimpleName();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Per-verticle collection of handler methods.
     * <p>
     * Handler method is a single-argument method that processes specific {@code Class} of messages.
     * </p>
     * <p>
     * For every message {@code Class} there should be no more that one handling method within verticle.
     * </p>
     */
    private final Map<Class<?>, HandlerContainer> handlers = new HashMap<>();


    @Override
    public void init(final Vertx vertx, final Context context) {
        super.init(vertx, context);
        registerHandlers();
    }

    private TransactionTemplate transactionTemplate;

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    public synchronized void initTransactions(
        @Qualifier("transactionManager") final PlatformTransactionManager platformTransactionManager
    ) {
        if (transactionTemplate == null) {
            transactionTemplate = new TransactionTemplate(platformTransactionManager);
            transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            logger.info("Transaction template set");
        }
    }

    protected void registerConsumer(final String address) {
        logger.debug("Consumer registered for: {}", address);
        vertx.eventBus().localConsumer(address, msg -> {
            val result = handleWithRespectToTransactions(msg);
            msg.reply(result);
        });
    }

    private <T> Object handleWithRespectToTransactions(
        final Message<T> messageData
    ) {
        final T data = messageData.body();
        val handler = handlers.get(data.getClass());
        if (handler.has(Transactional.class)) {
            return transactionTemplate.execute(status -> {
                try {
                    logger.trace("{} Transaction executing", className(), handler.methodName);
                    val result = handler.handle.invoke(this, data);
                    logger.trace("{} Transaction success", className(), handler.methodName);
                    return result;
                } catch (final Throwable throwable) {
                    logger.trace("{} Transaction error", className(), handler.methodName);
                    throw new RuntimeException(throwable);
                }
            });
        } else {
            logger.trace("{} No transaction needed", className(), handler.methodName);
            try {
                return handler.handle.invoke(this, data);
            } catch (final Throwable th) {
                throw new RuntimeException(th);
            }
        }
    }

    private void registerHandlers() {
        val allMethods = this.getClass().getMethods();
        val mhLookup = MethodHandles.lookup();
        val localMethods = this.getClass().getDeclaredMethods();
        fillInHandlersFromMethods(localMethods, mhLookup);
        fillInHandlersFromMethods(allMethods, mhLookup);

    }

    private void fillInHandlersFromMethods(final Method[] methods, final MethodHandles.Lookup mhLookup) {
        Arrays.stream(methods)
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .filter(method -> !method.isBridge() && !method.isSynthetic())
            .filter(method -> method.isAnnotationPresent(HandlerMethod.class))
            .filter(method -> method.getParameterCount() == 1)
            .forEach(foundMethod -> {
                final Class<?> dataType = foundMethod.getParameterTypes()[0];
                handlers.putIfAbsent(dataType, unreflect(foundMethod, mhLookup));
                logger.trace(
                    "Handler registered: {}.{}({})",
                    className(), foundMethod.getName(), dataType.getSimpleName()
                );
            });
    }

    protected String className() {
        return simpleClassName;
    }

    private HandlerContainer unreflect(final Method method, final MethodHandles.Lookup lookup) {
        try {
            return new HandlerContainer(
                className() + "#" + method.getName(),
                lookup.unreflect(method),
                Arrays.stream(method.getAnnotations()).map(Annotation::annotationType).collect(toList())
            );
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
