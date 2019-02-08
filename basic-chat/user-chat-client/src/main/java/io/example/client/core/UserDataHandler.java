package io.example.client.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.client.UnknownChatCommand;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.abstracts.AuthenticationResult;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.client.Routing;
import io.example.client.api.server.handling.TcpServerConnection;
import io.example.client.messages.OutputMessage;
import io.example.client.messages.StopConsole;
import io.example.client.messages.UserInputMessage;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public class UserDataHandler extends DataHandler {
    private static final Logger log = LoggerFactory.getLogger(UserDataHandler.class);
    private final TcpServerConnection tcpServerConnection;
    private final boolean allowUnknownCommands;

    private final AtomicReference<ClientId> clientId = new AtomicReference<>();
    private final AtomicReference<ClientState> clientState = new AtomicReference<>(ClientState.CONNECTED);

    public UserDataHandler(final TcpServerConnection tcpServerConnection, final boolean allowUnknownCommands) {
        this.tcpServerConnection = tcpServerConnection;
        this.allowUnknownCommands = allowUnknownCommands;
        tcpServerConnection.onClose(handler -> stopClient());
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.USER_DATA_HANDLER);
        super.start(startFuture);
    }

    @Override
    public void stop(final Future<Void> stopFuture) throws Exception {
        tcpServerConnection.close();
        super.stop(stopFuture);
    }

    @HandlerMethod
    public void handleUserInput(final UserInputMessage userInput) {
        switch (clientState.get()) {
            case AUTHENTICATING:
                final var clientId = new ClientId(userInput.getUserInput());
                tcpServerConnection.sendMessage(new AuthenticationResponse(clientId));
                break;
            case CONNECTED:
                log.warn("Client authentication is not complete, user input discarded");
                break;
            case DISCONECTING:
                log.info("Client is disconnecting, user input discarded");
                break;
            case ACTIVE:
                handleMessageOrCommand(userInput.getUserInput());
                break;
        }
    }

    private void stopClient() {
        sendMessageLocally(Routing.CONSOLE_CLIENT, new StopConsole(), onDelivery -> vertx.close(onClose -> {
            log.info("User left or server stopped, closing client: {}", onClose.succeeded());
            System.exit(0); //TDB: may there is a better way
        }));

    }

    private void handleMessageOrCommand(final String userInput) {
        final var command = CommandType.getByPrefix(userInput);
        switch (command) {
            case HELP:
            case STATS:
                tcpServerConnection.sendMessage(new ChatCommand(clientId.get(), command));
                break;
            case LEAVE:
                clientState.set(ClientState.DISCONECTING);
                tcpServerConnection.close();
                break;
            case UNKNOWN:
                handleUnknownCommand(userInput);
                break;
            case NONE:
                tcpServerConnection.sendMessage(new ChatMessage(userInput, clientId.get()));
                break;
        }
    }

    private void handleUnknownCommand(final String userInput) {
        if (allowUnknownCommands) {
            tcpServerConnection.sendMessage(new UnknownChatCommand(userInput, clientId.get()));
            log.info("Unknown command sent to server: {}", userInput);
        } else {
            outputMessage("Command unknown: '" + userInput + "'");
            log.info("Unknown commands prohibited.");
        }
    }

    @Override
    public void doHandle(final Buffer event) {
        final BaseChatMessage messageFromServer = tcpServerConnection.decode(event);
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
            case NONE:
                log.error("Incomprehensible message from server: {}", messageFromServer);
                break;
            default:
                outputMessage(messageFromServer.getMessage());
        }
    }

    private void handleNewChatMessage(final ChatMessage messageFromServer) {
        outputMessage(messageFromServer.getClientId() + ": " + messageFromServer.getMessage());
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
            authenticationResult.getLastMessages().stream()
                .sorted(Comparator.comparing(ChatMessage::getMessageTime))
                .forEach(this::handleNewChatMessage);
            log.info("Authentication success");
        } else {
            log.info("Authentication failed");
        }
    }

    private void outputMessage(final String message) {
        sendMessage(Routing.CONSOLE_CLIENT, new OutputMessage(message));
    }

}
