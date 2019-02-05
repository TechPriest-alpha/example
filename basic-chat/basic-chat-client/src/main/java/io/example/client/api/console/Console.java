package io.example.client.api.console;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.errors.GeneralInternalError;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.client.Routing;
import io.example.client.messages.OutputMessage;
import io.example.client.messages.StopConsole;
import io.example.client.messages.UserInputMessage;
import io.vertx.core.Future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringVerticle
public class Console extends BaseVerticle {
    private final ExecutorService ioExecutor;
    private final AtomicBoolean shouldRun = new AtomicBoolean(true);

    public Console() {
        this.ioExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.CONSOLE_CLIENT);
        startIo();
        super.start(startFuture);
    }

    @HandlerMethod
    public void outputMessage(final OutputMessage msg) {
        System.out.println(msg.getMessage());
    }

    @HandlerMethod
    public void logoutHandler(final StopConsole stopConsole) {
        shouldRun.set(false);
        try {
            ioExecutor.shutdown();
            ioExecutor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }

    private void startIo() {
        ioExecutor.submit(() -> {
            String userInput;
            do {
                userInput = System.console().readLine().trim();
                sendMessage(Routing.USER_DATA_HANDLER, new UserInputMessage(userInput));
                shouldRun.set(CommandType.getByPrefix(userInput).isNotLeave()); //trick to avoid redundant console reads
                log.info("Got next userInput '{}'", userInput);
            } while (shouldRun.get());
        });
    }

}
