package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.api.tcp.ServerSocketHandler;
import io.example.server.contracts.AuthenticatedClientContract;
import io.example.server.data.CommandResponse;
import io.example.server.data.DisconnectedClient;
import io.example.server.data.NewChatMessage;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class AuthenticatedClientManager extends BaseServerVerticle {
    private final AuthenticatedClientContract authenticatedClient;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.CONNECTED_CLIENT_MANAGERS); //for chat message publishing
        registerConsumer(Routing.CONNECTED_CLIENT_MANAGERS + "." + authenticatedClient.getClientId().getValue()); //for commands callback
        authenticatedClient.chatHandler(new ChatHandler(this));
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

    @HandlerMethod
    public void onCommandResponse(final CommandResponse commandResponse) {
        if (!commandResponse.getClientId().equals(authenticatedClient.getClientId())) {
            log.warn("Command response {} directed to wrong client {}", commandResponse, authenticatedClient.getClientId());
        } else {
            authenticatedClient.sendToClient(commandResponse.getChatMessage());
        }
    }

    @Slf4j
    public static final class ChatHandler extends ServerSocketHandler {
        private final AtomicLong messageCount = new AtomicLong(0L);
        private final AuthenticatedClientManager authenticatedClientManager;
        private final AuthenticatedClientContract client;

        ChatHandler(final AuthenticatedClientManager authenticatedClientManager) {
            this.authenticatedClientManager = authenticatedClientManager;
            this.client = authenticatedClientManager.authenticatedClient;
        }

        @Override
        public void doHandle(final Buffer event) {
            final var chatMessage = client.decode(event);
            switch (chatMessage.getMessageType()) {
                case AUTHENTICATION_REQUEST:
                case AUTHENTICATION_RESPONSE:
                    log.warn("Unexpected authentication message from client: {}", chatMessage);
                    break;
                case CHAT_TEXT:
                    handleChatMessage((ChatMessage) chatMessage);
                    break;
                case COMMAND:
                    handleCommand((ChatCommand) chatMessage);
                    break;
            }
        }

        private void handleChatMessage(final ChatMessage chatMessage) {
            if (!chatMessage.getClientId().equals(client.getClientId())) {
                log.warn("Received message from wrong client: {}, currentId: {}", chatMessage, client.getClientId());
                return;
            }
            final var newChatMessage = new NewChatMessage(client.getClientId(), chatMessage);
            authenticatedClientManager.publishMessage(Routing.CONNECTED_CLIENT_MANAGERS, newChatMessage);
            authenticatedClientManager.sendMessageLocally(Routing.MESSAGE_STORAGE, newChatMessage);
            log.debug("Chat message handled: {}", chatMessage);
            logBasicStats(chatMessage);
        }

        private void handleCommand(final ChatCommand chatCommand) {
            if (!chatCommand.getClientId().equals(client.getClientId())) {
                log.warn("Received command from wrong client: {}, currentId: {}", chatCommand, client.getClientId());
                return;
            }
            authenticatedClientManager.sendMessage(Routing.COMMAND_HANDLER, new NewCommandMessage(client.getClientId(), chatCommand));
            authenticatedClientManager.sendMessageLocally(Routing.MESSAGE_STORAGE, new NewChatMessage(client.getClientId(), chatCommand));
        }

        private void logBasicStats(final ChatMessage chatMessage) {

            log.debug("{} New chat message: {}", client.getClientId(), chatMessage);
            if (messageCount.incrementAndGet() % 100 == 0) {
                log.info("{} sent {} messages", client.getClientId(), messageCount);
            }
        }
    }
}
