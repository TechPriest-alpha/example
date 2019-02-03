package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.server.data.ClientInfo;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectedClientManager extends BaseVerticle {
    private final ClientInfo clientInfo;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        clientInfo.chatHandler(new ChatHandler(this));
        super.start(startFuture);
    }

    @RequiredArgsConstructor
    private class ChatHandler implements Handler<Buffer> {
        private final ConnectedClientManager connectedClientManager;

        @Override
        public void handle(final Buffer event) {
            final var chatMessage = Json.decodeValue(event, ChatMessage.class);
            switch (chatMessage.getMessageType()) {
                case AUTHENTICATION_REQUEST:
                case AUTHENTICATION_RESPONSE:
                    log.warn("Unexpected authentication message from client: {}", chatMessage);
                    break;
                case CHAT_TEXT:
                    log.info("New chat message: {}", chatMessage);
                    break;
            }
        }
    }
}
