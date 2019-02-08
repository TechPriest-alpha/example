package io.example.server.core;

import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.server.api.tcp.messages.NewClient;
import io.example.test.BaseVertxTest;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationWithTimeout;

import java.util.ResourceBundle;

import static io.example.server.api.tcp.TcpServer.DELIMITER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewClientsManagerTest extends BaseVertxTest {
    private final JsonMessageConverter messageConverter = new JsonMessageConverter();
    private NewClientsManager newClientsManager;
    private DataStorage dataStorage;
    private NetSocket socket;
    private final VerificationWithTimeout timeout = Mockito.timeout(1000);


    @BeforeEach
    void setUp() {
        dataStorage = new DataStorage();
        socket = Mockito.mock(NetSocket.class);
        newClientsManager = new NewClientsManager(dataStorage);
        deploy(newClientsManager);
    }

    @AfterEach
    void tearDown() {
        undeploy(newClientsManager.deploymentID());
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
        Mockito.verify(socket, timeout).write(Json.encode(new AuthenticationRequest()) + DELIMITER);
    }

    @Test
    void failedAuth() {
        final var client = nextClientId("testClient1");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new AuthenticationResponse(client)).appendString(DELIMITER));
        Mockito.verify(socket, timeout).write(messageConverter.encode(new AuthenticationResultFailure(client)) + DELIMITER);
    }

    @Test
    void successAuth() {
        final var client = nextClientId("testClient2");
        final var otherClient = nextClientId("otherTestClient2");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new AuthenticationResponse(otherClient)).appendString(DELIMITER));
        Mockito.verify(socket, timeout).write(messageConverter.encode(new AuthenticationResultSuccess(otherClient, dataStorage.lastMessages())) + DELIMITER);
    }

    @Test
    void wrongMessageFromClient() {
        final var client = nextClientId("testClient1");
        dataStorage.addClient(client);
        final var newClient = new NewClient(socket, messageConverter);
        final var authenticator = new NewClientsManager.Authenticator(newClientsManager, newClient);
        authenticator.handle(Json.encodeToBuffer(new ChatMessage("wrong message", client)).appendString(DELIMITER));
        Mockito.verify(socket, timeout).write(messageConverter.encode(new AuthenticationRequest()) + DELIMITER);
    }
}