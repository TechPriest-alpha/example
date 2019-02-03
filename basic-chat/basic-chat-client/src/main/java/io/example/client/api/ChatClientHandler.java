package io.example.client.api;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatClientHandler implements Handler<AsyncResult<NetSocket>> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    @Override
    public void handle(final AsyncResult<NetSocket> event) {
        final var socket = event.result();
        socket.handler(new DataHandler(socket));
        log.info("Client {} connected", "clientId");
    }
}
