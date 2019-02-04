package io.example.client.api.handling;

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

import java.util.concurrent.atomic.AtomicInteger;

@Value
public class DataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final ServerConnection serverConnection;
    private final Vertx vertx;
    private final String clientId;

    @Override
    public void handle(final Buffer event) {
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
                final var msg = new ChatMessage(RandomStringUtils.randomAlphabetic(10));
                serverConnection.sendMessage(msg);
//                ThreadUtils.awaitRandom(10, 100);
                final var currentStep = counter.incrementAndGet();
                log.debug("Next message sent: {}, {}", msg, currentStep);
                if (currentStep >= 500) {
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
