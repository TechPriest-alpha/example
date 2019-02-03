package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.dto.ChatMessage;
import io.example.server.Routing;
import io.vertx.core.Future;

@SpringVerticle
public class NewClientsManager extends BaseVerticle {

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.MESSAGE_HANDLER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void clientRequestsHandler(final ChatMessage msg) {

    }
}
