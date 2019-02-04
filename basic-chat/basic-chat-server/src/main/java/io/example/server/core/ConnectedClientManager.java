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
    private int messageCount;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        clientInfo.chatHandler(new ChatHandler());
        clientInfo.disconnectHandler(onDisconnect -> {
            vertx.undeploy(deploymentID());
            log.info("{} left", clientInfo.getClientId());
        });
        super.start(startFuture);
    }

    @RequiredArgsConstructor
    private final class ChatHandler implements Handler<Buffer> {

        @Override
        public void handle(final Buffer event) {
            final var chatMessage = Json.decodeValue(event, ChatMessage.class);
            switch (chatMessage.getMessageType()) {
                case AUTHENTICATION_REQUEST:
                case AUTHENTICATION_RESPONSE:
                    log.warn("Unexpected authentication message from client: {}", chatMessage);
                    break;
                case CHAT_TEXT:
                    messageCount++;
                    log.debug("{} New chat message: {}", clientInfo.getClientId(), chatMessage);
                    if (messageCount % 100 == 0) {
                        log.info("{} sent {} messages", clientInfo.getClientId(), messageCount);
                    }
                    break;
            }
        }
    }
}
