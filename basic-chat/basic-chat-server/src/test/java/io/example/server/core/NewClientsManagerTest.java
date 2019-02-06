package io.example.server.core;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.server.data.NewClient;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewClientsManagerTest {
    private final JsonMessageConverter messageConverter = new JsonMessageConverter();
    private NewClientsManager newClientsManager;
    private DataStorage dataStorage;
    private NetSocket socket;

    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        socket = Mockito.mock(NetSocket.class);
        newClientsManager = new NewClientsManager(dataStorage);
        Vertx.vertx().deployVerticle(newClientsManager);
    }

    @AfterEach
    void tearDown() {
        Vertx.vertx().undeploy(newClientsManager.deploymentID());
    }

    @Test
    void testMessageRetrieval() {
        final var bundle = ResourceBundle.getBundle("messages/CommonMessage");
        assertNotNull(bundle);
        assertEquals(new AuthenticationRequest().getMessage(), "Please introduce yourself");
    }

    @Test
    void testAuthRequestSentForNewClient() {
        final var newClient = new NewClient(socket, messageConverter);
        newClientsManager.clientRequestsHandler(newClient);
        Mockito.verify(socket).write(Json.encode(new AuthenticationRequest()));
    }

    @Test
    void failedAuth() {
        final var client = new ClientId("testClient1");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new AuthenticationResponse(client)));
        Mockito.verify(socket).write(messageConverter.encode(new AuthenticationResultFailure(client)));
    }

    @Test
    void successAuth() {
        final var client = new ClientId("testClient2");
        final var otherClient = new ClientId("otherTestClient2");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new AuthenticationResponse(otherClient)));
        Mockito.verify(socket).write(messageConverter.encode(new AuthenticationResultSuccess(otherClient, dataStorage.lastMessages())));
    }

    @Test
    void wrongMessageFromClient() {
        final var client = new ClientId("testClient1");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new ChatMessage("wrong message", client)));
        Mockito.verify(socket).write(messageConverter.encode(new AuthenticationRequest()));
    }
}