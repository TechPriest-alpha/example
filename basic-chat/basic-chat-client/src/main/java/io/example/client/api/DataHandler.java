package io.example.client.api;

import io.example.auxiliary.message.chat.AuthResponse;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.ChatMessage;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final NetSocket socket;

    @Override
    public void handle(final Buffer event) {
        final BaseChatMessage messageFromServer = Json.decodeValue(event, ChatMessage.class);
        log.info("Msg: {}", messageFromServer);
        if (messageFromServer.getMessageType().isAuthenticationRequest()) {
            final String clientId = "ClientId";
            socket.write(Json.encode(new AuthResponse(clientId)));
            log.info("Authentication response: {}", clientId);
        }

    }
}
