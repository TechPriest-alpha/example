package io.example.client.api;

import io.example.auxiliary.helpers.ThreadUtils;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Value
public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final ServerConnection serverConnection;
    private final Vertx vertx;

    @Override
    public void handle(final Buffer event) {
        final BaseChatMessage messageFromServer = serverConnection.decode(event);
        log.info("Msg: {}", messageFromServer);
        if (messageFromServer.getMessageType().isAuthenticationRequest()) {
            handleAuthenticationRequest();
        } else if (messageFromServer.getMessageType().isAuthenticationResult()) {
            handleAuthenticationResult((AuthenticationResult) messageFromServer);
        }
    }

    private void handleAuthenticationRequest() {
        final String clientId = RandomStringUtils.randomAlphanumeric(5);
        serverConnection.sendMessage(new AuthenticationResponse(clientId));
        log.info("Authentication response: {}", clientId);
    }

    private void handleAuthenticationResult(final AuthenticationResult messageFromServer) {
        if (messageFromServer.getVerdict().success()) {
            vertx.executeBlocking(handler -> {
                for (int i = 0; i < 10; i++) {
                    final var msg = new ChatMessage(RandomStringUtils.randomAlphabetic(10));
                    serverConnection.sendMessage(msg);
                    log.info("Next message sent: {}", msg);
                    ThreadUtils.await(2000);
                }
            }, result -> {
            });
        } else {
            handleAuthenticationRequest();
        }
    }
}
