package io.example.client.core;

import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.client.api.handling.ChatClientHandler;
import io.example.client.api.handling.ServerConnection;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

@Value
public class ConsoleUserDataHandler implements Handler<Buffer> {
    private static final Logger log = LoggerFactory.getLogger(ChatClientHandler.class);
    private final ServerConnection serverConnection;
    private final Vertx vertx;
    private final ExecutorService ioExecutor;
    private final AtomicReference<String> clientId = new AtomicReference<>();
    private final AtomicReference<ClientState> clientState = new AtomicReference<>(ClientState.CONNECTED);

    public ConsoleUserDataHandler(final ServerConnection serverConnection, final Vertx vertx) {
        this.serverConnection = serverConnection;
        this.vertx = vertx;
        this.ioExecutor = Executors.newSingleThreadExecutor();
        serverConnection.onClose(handler -> stopClient());
        startIo();
    }

    private void startIo() {
        ioExecutor.submit(() -> {
            String userInput;
            do {
                userInput = System.console().readLine().trim();
                handleUserInput(userInput);
                log.info("Got next userInput '{}'", userInput);
            } while (!"leave".equalsIgnoreCase(userInput));
            serverConnection.close();
        });
    }

    private void stopClient() {
        vertx.close(onClose -> {
            log.info("User left or server stopped, closing client: {}", onClose.succeeded());
            System.exit(0); //TDB: may there is a better way
        });
    }

    private void handleUserInput(final String userInput) {
        switch (clientState.get()) {
            case AUTHENTICATING:
                serverConnection.sendMessage(new AuthenticationResponse(userInput));
                break;
            case CONNECTED:
                log.warn("Client authentication is not complete, user input discarded");
                break;
            case ACTIVE:
                handleMessageOrCommand(userInput);
                break;
        }
    }

    private void handleMessageOrCommand(final String userInput) {
        final var command = CommandType.getByPrefix(userInput);
        if (command.isNone()) {
            serverConnection.sendMessage(new ChatMessage(userInput, clientId.get()));
        } else {
            serverConnection.sendMessage(new ChatCommand(command));
        }
    }

    @Override
    public void handle(final Buffer event) {
        final BaseChatMessage messageFromServer = serverConnection.decode(event);
        log.debug("Msg: {}", messageFromServer);
        switch (messageFromServer.getMessageType()) {
            case AUTHENTICATION_REQUEST:
                handleAuthenticationRequest((AuthenticationRequest) messageFromServer);
                break;
            case AUTHENTICATION_RESULT:
                handleAuthenticationResult((AuthenticationResult) messageFromServer);
                break;
            case CHAT_TEXT:
                handleNewChatMessage((ChatMessage) messageFromServer);
                break;
            case AUTHENTICATION_RESPONSE:
            case COMMAND:
                log.error("Client does not support such message: {}", messageFromServer);
                break;
        }
    }

    private void handleNewChatMessage(final ChatMessage messageFromServer) {
        outputMessage(messageFromServer.getClientId() + ":" + messageFromServer.getMessage());
    }

    private void handleAuthenticationRequest(final AuthenticationRequest messageFromServer) {
        clientState.set(ClientState.AUTHENTICATING);
        outputMessage(messageFromServer.getMessage());
        log.info("Authentication request received");
    }

    private void handleAuthenticationResult(final AuthenticationResult authenticationResult) {
        outputMessage(authenticationResult.getMessage());
        if (authenticationResult.getVerdict().success()) {
            clientId.set(authenticationResult.getClientId());
            clientState.set(ClientState.ACTIVE);
            authenticationResult.getLastMessages().forEach(msg -> System.console().writer().write(msg.getMessage()));
            log.info("Authentication success");
        } else {
            log.info("Authentication failed");
        }
    }

    private void outputMessage(final String message) {
        System.out.println(message);
//        System.console().flush();
    }

}
