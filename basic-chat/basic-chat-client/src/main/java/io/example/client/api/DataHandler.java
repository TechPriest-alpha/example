package io.example.client.api;

import io.example.auxiliary.helpers.ThreadUtils;
import io.example.auxiliary.message.chat.AuthResponse;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.ChatMessage;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final NetSocket socket;
    private final Vertx vertx;

    @Override
    public void handle(final Buffer event) {
        final BaseChatMessage messageFromServer = Json.decodeValue(event, ChatMessage.class);
        log.info("Msg: {}", messageFromServer);
        if (messageFromServer.getMessageType().isAuthenticationRequest()) {
            final String clientId = "ClientId";
            socket.write(Json.encode(new AuthResponse(clientId)));
            log.info("Authentication response: {}", clientId);
        }
        vertx.executeBlocking(handler -> {
            for (int i = 0; i < 10; i++) {
                final var msg = new ChatMessage(RandomStringUtils.randomAlphabetic(10));
                socket.write(Json.encode(msg));
                log.info("Next message sent: {}", msg);
                ThreadUtils.await(2000);
            }
        }, result -> {
        });


    }
}
