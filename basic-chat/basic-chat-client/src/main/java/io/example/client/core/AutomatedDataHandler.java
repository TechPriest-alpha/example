package io.example.client.core;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.abstracts.AuthenticationResult;
import io.example.client.api.server.handling.ChatClientHandler;
import io.example.client.api.server.handling.ServerConnection;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

@Value
public class AutomatedDataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final ServerConnection serverConnection;
    private final Vertx vertx;
    private final ClientId clientId;

    public AutomatedDataHandler(final ServerConnection serverConnection, final Vertx vertx) {
        this.serverConnection = serverConnection;
        this.vertx = vertx;
        this.clientId = new ClientId(RandomStringUtils.randomAlphanumeric(5));
    }

    @Override
    public void handle(final Buffer event) {
        log.info("Client {} connected", clientId);

        final BaseChatMessage messageFromServer = serverConnection.decode(event);
        log.debug("Msg: {}", messageFromServer);
        if (messageFromServer.getMessageType().isAuthenticationRequest()) {
            handleAuthenticationRequest();
        } else if (messageFromServer.getMessageType().isAuthenticationResult()) {
            handleAuthenticationResult((AuthenticationResult) messageFromServer);
        }
    }

    private void handleAuthenticationRequest() {
        serverConnection.sendMessage(new AuthenticationResponse(clientId));
        log.debug("Authentication response: {}", clientId);
    }

    private void handleAuthenticationResult(final AuthenticationResult messageFromServer) {
        if (messageFromServer.getVerdict().success()) {
            final var counter = new AtomicInteger(0);
            vertx.setPeriodic(10, timer -> {
                final var msg = new ChatMessage(RandomStringUtils.randomAlphabetic(10), clientId);
                serverConnection.sendMessage(msg);
//                ThreadUtils.awaitRandom(10, 100);
                final var currentStep = counter.incrementAndGet();
                log.debug("Next message sent: {}, {}", msg, currentStep);
                if (currentStep >= 50) {
                    log.info("{} all messages sent", clientId);
                    vertx.cancelTimer(timer);
                } else if (currentStep % 100 == 0) {
                    log.info("{} sent {} messages", clientId, currentStep);
                }
            });

        } else {
            handleAuthenticationRequest();
        }
    }
}
