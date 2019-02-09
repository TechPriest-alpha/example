package io.example.client.core;

import io.example.auxiliary.helpers.ThreadUtils;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.abstracts.AuthenticationResult;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class AutomatedDataHandler extends DataHandler {
    private static final Logger log = LoggerFactory.getLogger(AutomatedDataHandler.class);

    private static final AtomicInteger cnt = new AtomicInteger(0);
    private final ServerConnectionContract tcpServerConnection;
    private final boolean allowUnknownCommands;
    private final ClientId clientId;

    public AutomatedDataHandler(final ServerConnectionContract tcpServerConnection, final boolean allowUnknownCommands) {
        this.tcpServerConnection = tcpServerConnection;
        this.allowUnknownCommands = allowUnknownCommands;
        this.clientId = new ClientId(cnt.incrementAndGet() + "_" + RandomStringUtils.randomAlphanumeric(5));
        tcpServerConnection.onClose(closeEvent -> log.info("Client {} closed connection", clientId));
    }

    @Override
    public void stop(final Future<Void> stopFuture) throws Exception {
        tcpServerConnection.close();
        super.stop(stopFuture);
    }

    @Override
    public void handle(final Buffer event) {
        try {
            final BaseChatMessage messageFromServer = tcpServerConnection.decode(event);
            log.debug("Msg: {}", messageFromServer);
            if (messageFromServer.getMessageType().isAuthenticationRequest()) {
                handleAuthenticationRequest();
            } else if (messageFromServer.getMessageType().isAuthenticationResult()) {
                handleAuthenticationResult((AuthenticationResult) messageFromServer);
            } else if (messageFromServer.getMessageType().isChatMessage()) {
                log.debug("Client {} received message {} from client {}", clientId, messageFromServer.getMessage(), ((ChatMessage) messageFromServer).getClientId());
            }
        } catch (final Exception ex) {
            log.error("Error on data: " + event, ex);
        }

    }

    private void handleAuthenticationRequest() {
        tcpServerConnection.sendMessage(new AuthenticationResponse(clientId));
        log.debug("Authentication response: {}", clientId);
    }

    private void handleAuthenticationResult(final AuthenticationResult messageFromServer) {
        if (messageFromServer.getVerdict().success()) {
            final var counter = new AtomicInteger(0);
            vertx.setPeriodic(10, timer -> {
                final var msg = new ChatMessage(RandomStringUtils.randomAlphabetic(10), clientId);
                tcpServerConnection.sendMessage(msg);
                ThreadUtils.awaitRandom(100, 200);
                final var currentStep = counter.incrementAndGet();
                log.debug("Next message sent: {}, {}", msg, currentStep);
                if (currentStep >= 1500) {
                    log.info("{} all messages sent", clientId);
                    vertx.cancelTimer(timer);
                    tcpServerConnection.close();
                } else if (currentStep % 50 == 0) {
                    log.info("{} sent {} messages", clientId, currentStep);
                }
            });

        } else {
            handleAuthenticationRequest();
        }
    }
}
