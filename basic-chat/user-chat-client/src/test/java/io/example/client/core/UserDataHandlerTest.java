package io.example.client.core;

import io.example.auxiliary.helpers.ThreadUtils;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.client.UnknownChatCommand;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.auxiliary.message.chat.server.HelpResponse;
import io.example.client.Routing;
import io.example.client.api.server.handling.TcpServerConnection;
import io.example.client.messages.OutputMessage;
import io.example.client.messages.UserInputMessage;
import io.example.test.BaseVertxTest;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static io.example.client.api.server.ChatTcpClient.DELIMITER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDataHandlerTest extends BaseVertxTest {

    private UserDataHandler userDataHandler;
    private NetSocket socket;
    private MessageConverter messageConverter;

    @BeforeEach
    void setUp() {
        messageConverter = new JsonMessageConverter();
        socket = Mockito.mock(NetSocket.class);
        userDataHandler = new UserDataHandler(new TcpServerConnection(socket, messageConverter), true);
        deploy(userDataHandler);
    }

    @AfterEach
    void tearDown() {
        undeploy(userDataHandler.deploymentID());
    }

    @Test
    void authenticationRequestHandling() {
        startAuthentication("");
        final var authenticationRequest = new AuthenticationRequest();
        addConsumer(Routing.CONSOLE_CLIENT);
        userDataHandler.handle(Json.encodeToBuffer(authenticationRequest).appendString(DELIMITER));
        assertEquals(awaitCompletion(), new OutputMessage(authenticationRequest.getMessage()));
    }

    @Test
    void authenticationResultSuccess() {
        final ClientId clientId = startAuthentication("authSuccess");
        awaitCompletion();
        final ChatMessage chatMessage1 = new ChatMessage("testMsg1", nextClientId("test msg1"));
        ThreadUtils.await(300);
        final ChatMessage chatMessage2 = new ChatMessage("testMsg12", nextClientId("test msg2"));
        final var authenticationResultSuccess = new AuthenticationResultSuccess(
            clientId, Arrays.asList(chatMessage1, chatMessage2)
        );
        userDataHandler.handle(Json.encodeToBuffer(authenticationResultSuccess).appendString(DELIMITER));

        assertEquals(awaitCompletion(), new OutputMessage(authenticationResultSuccess.getMessage()));
        assertEquals(awaitCompletion(), new OutputMessage(chatMessage1.getClientId().getValue() + ": " + chatMessage1.getMessage()));
        assertEquals(awaitCompletion(), new OutputMessage(chatMessage2.getClientId().getValue() + ": " + chatMessage2.getMessage()));
    }

    @Test
    void authenticationResultFailure() {
        final ClientId clientId = startAuthentication("authFailure");
        awaitCompletion();
        final var authenticationResultFailure = new AuthenticationResultFailure(clientId);
        userDataHandler.handle(Json.encodeToBuffer(authenticationResultFailure).appendString(DELIMITER));
        assertEquals(awaitCompletion(), new OutputMessage(authenticationResultFailure.getMessage()));
    }

    @Test
    void newChatMessage() {
        final var clientId = startAuthentication("leaveCommand");
        final var otherClient = nextClientId("otherClient");
        awaitCompletion();
        authenticationSuccess(clientId);
        final var chatMessage = new ChatMessage("new chat msg", otherClient);
        userDataHandler.handle(Json.encodeToBuffer(chatMessage).appendString(DELIMITER));
        assertEquals(awaitCompletion(), new OutputMessage(chatMessage.getClientId().getValue() + ": " + chatMessage.getMessage()));
    }

    @Test
    void commandResponse() {
        final var clientId = startAuthentication("leaveCommand");
        awaitCompletion();
        authenticationSuccess(clientId);
        final var helpMessage = new HelpResponse();
        userDataHandler.handle(Json.encodeToBuffer(helpMessage).appendString(DELIMITER));
        assertEquals(awaitCompletion(), new OutputMessage(helpMessage.getMessage()));
    }

    @Test
    void unknownMessageHandled() {
        final var clientId = startAuthentication("unknownMessage");
        awaitCompletion();
        authenticationSuccess(clientId);
        final var helpMessage = new UnknownHelpResponse("anything");
        userDataHandler.handle(Json.encodeToBuffer(helpMessage).appendString(DELIMITER));
        assertEquals(awaitCompletion(), new OutputMessage("anything"));
    }

    @Test
    void onLeaveConnectionClosed() {
        final ClientId clientId = startAuthentication("leaveCommand");
        awaitCompletion();
        authenticationSuccess(clientId);
        userDataHandler.handleUserInput(new UserInputMessage("!leave"));
        Mockito.verify(socket, Mockito.times(0)).write(Mockito.anyString());
        Mockito.verify(socket).close();
    }

    @Test
    void authenticationResponseSentToServer() {
        final ClientId clientId = startAuthentication("authResponse");
        awaitCompletion();
        userDataHandler.handleUserInput(new UserInputMessage(clientId.getValue()));
        Mockito.verify(socket).write(messageConverter.encode(new AuthenticationResponse(clientId)) + DELIMITER);
    }

    @Test
    void chatMessageSentToServer() {
        final ClientId clientId = startAuthentication("authResponse");
        awaitCompletion();
        authenticationSuccess(clientId);
        userDataHandler.handleUserInput(new UserInputMessage("new chat message"));
        final var encoded = messageConverter.encode(new ChatMessage("new chat message", clientId));
        Mockito.verify(socket).write(Mockito.startsWith(encoded.substring(0, encoded.indexOf("messageTime"))) + DELIMITER);
    }

    @Test
    void commandSentToServer() {
        final ClientId clientId = startAuthentication("authResponse");
        awaitCompletion();
        authenticationSuccess(clientId);
        userDataHandler.handleUserInput(new UserInputMessage("!command"));
        final var encoded = messageConverter.encode(new UnknownChatCommand("!command", clientId));
        Mockito.verify(socket).write(Mockito.startsWith(encoded.substring(0, encoded.indexOf("messageTime"))) + DELIMITER);
    }

    private void authenticationSuccess(final ClientId clientId) {
        userDataHandler.handle(Json.encodeToBuffer(new AuthenticationResultSuccess(clientId, Collections.emptyList())).appendString(DELIMITER));
        awaitCompletion();

    }

    private ClientId startAuthentication(final String client) {
        final var authenticationRequest = new AuthenticationRequest();
        final var clientId = nextClientId(client);
        userDataHandler.handle(Json.encodeToBuffer(authenticationRequest).appendString(DELIMITER));
        addConsumer(Routing.CONSOLE_CLIENT);
        return clientId;
    }
}