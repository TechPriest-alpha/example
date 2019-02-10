package io.example.server.core;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.server.Routing;
import io.example.server.api.tcp.messages.AuthenticatedClient;
import io.example.server.data.CommandResponse;
import io.example.server.data.NewChatMessage;
import io.example.server.data.NewCommandMessage;
import io.example.test.BaseVertxTest;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.example.server.api.tcp.TcpServer.DELIMITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;

class AuthenticatedClientManagerTest extends BaseVertxTest {
    private final JsonMessageConverter messageConverter = new JsonMessageConverter();
    private ClientId testClient;
    private AuthenticatedClientManager authenticatedClientManager;
    private NetSocket socket;

    @BeforeEach
    void setUp() {
        socket = Mockito.mock(NetSocket.class);
        testClient = nextClientId("authenticatedTestClient");
        final AuthenticatedClient authenticatedClient = new AuthenticatedClient(socket, testClient, messageConverter);
        authenticatedClientManager = new AuthenticatedClientManager(authenticatedClient);

        deploy(authenticatedClientManager);
        authenticatedClientManager.init(vertx, vertx.getOrCreateContext());
    }

    @AfterEach
    void tearDown() {
        undeploy(authenticatedClientManager.deploymentID());
    }

    @Test
    @DisplayName("Verify that only messages from other clients are pushed, so there is no echo in chat.")
    void newChatMessageSentToClient() {
        final var otherClient = nextClientId("OtherTestClient");
        final var chatMessage = new ChatMessage("test msg", otherClient);
        authenticatedClientManager.onNewChatMessage(new NewChatMessage(otherClient, chatMessage));
        Mockito.verify(socket).write(messageConverter.encode(chatMessage) + DELIMITER);
    }

    @Test
    @DisplayName("Verify that message from the same client is not pushed, so there is no echo in chat.")
    void messageFromSelfIsNotSent() {
        final var chatMessage = new ChatMessage("test msg", testClient);
        authenticatedClientManager.onNewChatMessage(new NewChatMessage(testClient, chatMessage));
        Mockito.verify(socket, times(0)).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Verify that only responses to commands of 'this' client are sent.")
    void commandResponseIsSentToClient() {
        final var chatMessage = new ChatMessage("cmd response", testClient);
        authenticatedClientManager.onCommandResponse(new CommandResponse(testClient, chatMessage));
        Mockito.verify(socket).write(messageConverter.encode(chatMessage) + DELIMITER);
    }

    @Test
    @DisplayName("Verify that responses to commands of other clients are not sent.")
    void commandResponseIsNotSentToClient() {

        final var otherClient = nextClientId("OtherTestClient");
        final var chatMessage = new ChatMessage("test msg", otherClient);
        authenticatedClientManager.onCommandResponse(new CommandResponse(otherClient, chatMessage));
        Mockito.verify(socket, times(0)).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Chat message published and sent to store")
    void chatMessageHandled() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var chatMessage = new ChatMessage("msg from client", testClient);
        final var newChatMessage = new NewChatMessage(testClient, chatMessage);
        addConsumer(Routing.CONNECTED_CLIENT_MANAGERS);
        addConsumer(Routing.MESSAGE_STORAGE);

        handler.handle(Json.encodeToBuffer(chatMessage).appendString(DELIMITER));
        assertEquals(awaitCompletion(), newChatMessage);
        assertEquals(awaitCompletion(), newChatMessage);
    }

    @Test
    @DisplayName("Chat message from unmatched client discarded")
    void chatMessageDiscarded() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var otherClient = nextClientId("otherTestClient");
        final var chatMessage = new ChatMessage("msg from other client", otherClient);
        addConsumer(Routing.CONNECTED_CLIENT_MANAGERS);
        addConsumer(Routing.MESSAGE_STORAGE);

        handler.handle(Json.encodeToBuffer(chatMessage));
        assertNull(awaitCompletion());
    }

    @Test
    @DisplayName("Command sent to command handler")
    void commandSentToHandler() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var command = new ChatCommand(testClient, CommandType.UNKNOWN);
        final var newCommandMessage = new NewCommandMessage(testClient, command);
        addConsumer(Routing.COMMAND_HANDLER);

        handler.handle(Json.encodeToBuffer(command).appendString(DELIMITER));

        assertEquals(awaitCompletion(), newCommandMessage);

    }

    @Test
    @DisplayName("Command from wrong client discarded")
    void commandDiscarded() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var otherClient = nextClientId("otherTestClient");

        final var command = new ChatCommand(otherClient, CommandType.UNKNOWN);
        addConsumer(Routing.COMMAND_HANDLER);

        handler.handle(Json.encodeToBuffer(command));

        assertNull(awaitCompletion());
    }
}