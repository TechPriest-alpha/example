package io.example.client.api;

import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class ChatClientHandler implements Handler<AsyncResult<NetSocket>> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final Vertx vertx;
    private final MessageConverter messageConverter;

    @Override
    public void handle(final AsyncResult<NetSocket> event) {
        final var socket = event.result();
        socket.handler(new DataHandler(new ServerConnection(socket, messageConverter), vertx));
        log.info("Client {} connected", "clientId");
    }
}
