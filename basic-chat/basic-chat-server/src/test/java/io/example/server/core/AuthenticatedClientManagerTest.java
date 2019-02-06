package io.example.server.core;

import io.example.auxiliary.eventbus.BaseCodec;
import io.example.auxiliary.eventbus.BaseDeliveryOptions;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.server.Routing;
import io.example.server.data.AuthenticatedClient;
import io.example.server.data.CommandResponse;
import io.example.server.data.NewChatMessage;
import io.example.server.data.NewCommandMessage;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AuthenticatedClientManagerTest {
    private final JsonMessageConverter messageConverter = new JsonMessageConverter();
    private final ClientId testClient = new ClientId("authenticatedTestClient");
    private AuthenticatedClientManager authenticatedClientManager;
    private NetSocket socket;
    private Vertx vertx;
    private EventBus eventBus;

    @BeforeEach
    void setUp() {
        socket = Mockito.mock(NetSocket.class);

        final AuthenticatedClient authenticatedClient = new AuthenticatedClient(socket, testClient, messageConverter);
        authenticatedClientManager = new AuthenticatedClientManager(authenticatedClient);
        vertx = Mockito.spy(Vertx.vertx());
        eventBus = Mockito.spy(vertx.eventBus());
        Mockito.when(vertx.eventBus()).thenReturn(eventBus);
        vertx.eventBus().registerCodec(new BaseCodec());
        vertx.deployVerticle(authenticatedClientManager);
        authenticatedClientManager.init(vertx, vertx.getOrCreateContext());
    }

    @AfterEach
    void tearDown() {
        vertx.undeploy(authenticatedClientManager.deploymentID());
    }

    @Test
    @DisplayName("Verify that only messages from other clients are pushed, so there is no echo in chat.")
    void newChatMessageSentToClient() {
        final var otherClient = new ClientId("OtherTestClient");
        final var chatMessage = new ChatMessage("test msg", otherClient);
        authenticatedClientManager.onNewChatMessage(new NewChatMessage(otherClient, chatMessage));
        Mockito.verify(socket).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Verify that message from the same client is not pushed, so there is no echo in chat.")
    void messageFromSelfIsNotSent() {
        final var chatMessage = new ChatMessage("test msg", testClient);
        authenticatedClientManager.onNewChatMessage(new NewChatMessage(testClient, chatMessage));
        Mockito.verify(socket, Mockito.times(0)).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Verify that only responses to commands of 'this' client are sent.")
    void commandResponseIsSentToClient() {
        final var chatMessage = new ChatMessage("cmd response", testClient);
        authenticatedClientManager.onCommandResponse(new CommandResponse(testClient, chatMessage));
        Mockito.verify(socket).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Verify that responses to commands of other clients are not sent.")
    void commandResponseIsNotSentToClient() {

        final var otherClient = new ClientId("OtherTestClient");
        final var chatMessage = new ChatMessage("test msg", otherClient);
        authenticatedClientManager.onCommandResponse(new CommandResponse(otherClient, chatMessage));
        Mockito.verify(socket, Mockito.times(0)).write(messageConverter.encode(chatMessage));
    }

    @Test
    @DisplayName("Chat message published and sent to store")
    void chatMessageHandled() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var chatMessage = new ChatMessage("msg from client", testClient);
        final var newChatMessage = new NewChatMessage(testClient, chatMessage);
        handler.handle(Json.encodeToBuffer(chatMessage));
        Mockito.verify(eventBus).publish(Mockito.eq(Routing.CONNECTED_CLIENT_MANAGERS), Mockito.eq(newChatMessage), Mockito.any(BaseDeliveryOptions.class));
        Mockito.verify(eventBus).send(Mockito.eq(Routing.MESSAGE_STORAGE), Mockito.eq(newChatMessage), Mockito.any(BaseDeliveryOptions.class));
    }

    @Test
    @DisplayName("Chat message from unmatched client discarded")
    void chatMessageDiscarded() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var otherClient = new ClientId("otherTestClient");
        final var chatMessage = new ChatMessage("msg from other client", otherClient);
        final var newChatMessage = new NewChatMessage(otherClient, chatMessage);
        final var wrongChatMessage = new NewChatMessage(testClient, chatMessage);
        handler.handle(Json.encodeToBuffer(chatMessage));
        Mockito.verify(eventBus, Mockito.times(0)).publish(Mockito.eq(Routing.CONNECTED_CLIENT_MANAGERS), Mockito.eq(newChatMessage), Mockito.any(BaseDeliveryOptions.class));
        Mockito.verify(eventBus, Mockito.times(0)).publish(Mockito.eq(Routing.CONNECTED_CLIENT_MANAGERS), Mockito.eq(wrongChatMessage), Mockito.any(BaseDeliveryOptions.class));
        Mockito.verify(eventBus, Mockito.times(0)).send(Mockito.eq(Routing.MESSAGE_STORAGE), Mockito.eq(newChatMessage), Mockito.any(BaseDeliveryOptions.class));
        Mockito.verify(eventBus, Mockito.times(0)).send(Mockito.eq(Routing.MESSAGE_STORAGE), Mockito.eq(wrongChatMessage), Mockito.any(BaseDeliveryOptions.class));
    }

    @Test
    @DisplayName("Command sent to command handler")
    void commandSentToHandler() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var command = new ChatCommand(testClient, CommandType.NONE);
        final var newCommandMessage = new NewCommandMessage(testClient, command);
        handler.handle(Json.encodeToBuffer(command));
        Mockito.verify(eventBus).send(Mockito.eq(Routing.COMMAND_HANDLER), Mockito.eq(newCommandMessage), Mockito.any(BaseDeliveryOptions.class));
    }

    @Test
    @DisplayName("Command from wrong client discarded")
    void commandDiscarded() {
        final var handler = new AuthenticatedClientManager.ChatHandler(authenticatedClientManager);
        final var otherClient = new ClientId("otherTestClient");

        final var command = new ChatCommand(otherClient, CommandType.NONE);
        final var newCommandMessage = new NewCommandMessage(otherClient, command);
        final var wrongCommandMessage = new NewCommandMessage(testClient, command);
        handler.handle(Json.encodeToBuffer(command));
        Mockito.verify(eventBus, Mockito.times(0)).send(Mockito.eq(Routing.COMMAND_HANDLER), Mockito.eq(newCommandMessage), Mockito.any(BaseDeliveryOptions.class));
        Mockito.verify(eventBus, Mockito.times(0)).send(Mockito.eq(Routing.COMMAND_HANDLER), Mockito.eq(wrongCommandMessage), Mockito.any(BaseDeliveryOptions.class));
    }
}