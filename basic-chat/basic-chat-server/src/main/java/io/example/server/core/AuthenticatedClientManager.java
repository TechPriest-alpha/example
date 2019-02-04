package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.server.Routing;
import io.example.server.data.AuthenticatedClient;
import io.example.server.data.DisconnectedClient;
import io.example.server.data.NewChatMessage;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticatedClientManager extends BaseVerticle {
    private final AuthenticatedClient authenticatedClient;
    private int messageCount;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.CONNECTED_CLIENT_MANAGERS); //for chat message publishing
        registerConsumer(Routing.CONNECTED_CLIENT_MANAGERS + "." + authenticatedClient.getClientId()); //for commands callback
        authenticatedClient.chatHandler(new ChatHandler());
        authenticatedClient.disconnectHandler(onDisconnect -> {
            sendMessage(Routing.MESSAGE_STORAGE, new DisconnectedClient(authenticatedClient.getClientId()));
            vertx.undeploy(deploymentID());
            log.info("{} left", authenticatedClient.getClientId());
        });
        super.start(startFuture);
    }

    @HandlerMethod
    public void onNewChatMessage(final NewChatMessage newChatMessage) {
        if (!newChatMessage.getClientId().equals(authenticatedClient.getClientId())) {
            authenticatedClient.sendToClient(newChatMessage.getChatMessage());
        }
    }

    @RequiredArgsConstructor
    private final class ChatHandler implements Handler<Buffer> {

        @Override
        public void handle(final Buffer event) {
            final var chatMessage = authenticatedClient.decode(event);
            switch (chatMessage.getMessageType()) {
                case AUTHENTICATION_REQUEST:
                case AUTHENTICATION_RESPONSE:
                    log.warn("Unexpected authentication message from client: {}", chatMessage);
                    break;
                case CHAT_TEXT:
                    handleChatMessage((ChatMessage) chatMessage);
                    break;
                case COMMAND:
                    sendMessage(Routing.COMMAND_HANDLER, new NewCommandMessage(authenticatedClient.getClientId(), (ChatCommand) chatMessage));
                    break;
            }
        }

        private void handleChatMessage(final ChatMessage chatMessage) {
            final var newChatMessage = new NewChatMessage(authenticatedClient.getClientId(), chatMessage);
            publishMessage(Routing.CONNECTED_CLIENT_MANAGERS, newChatMessage);
            sendMessageLocally(Routing.MESSAGE_STORAGE, newChatMessage);
            logBasicStats(chatMessage);
        }

        private void logBasicStats(final ChatMessage chatMessage) {
            messageCount++;
            log.debug("{} New chat message: {}", authenticatedClient.getClientId(), chatMessage);
            if (messageCount % 100 == 0) {
                log.info("{} sent {} messages", authenticatedClient.getClientId(), messageCount);
            }
        }
    }
}
