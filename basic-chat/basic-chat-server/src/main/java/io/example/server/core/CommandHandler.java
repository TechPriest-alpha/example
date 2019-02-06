package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Future;

@SpringVerticle
public class CommandHandler extends BaseServerVerticle {

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.COMMAND_HANDLER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void processCommand(final NewCommandMessage newCommandMessage) {
        log.info("New command arrived: {}", newCommandMessage);
    }
}
