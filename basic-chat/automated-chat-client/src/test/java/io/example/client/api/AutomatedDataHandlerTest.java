package io.example.client.api;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.client.api.server.handling.TcpServerConnection;
import io.example.client.core.AutomatedDataHandler;
import io.example.test.BaseVertxTest;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static io.example.client.api.server.ChatTcpClient.DELIMITER;

class AutomatedDataHandlerTest extends BaseVertxTest {
    private AutomatedDataHandler automatedDataHandler;
    private TcpServerConnection socket;
    private Vertx vertx;

    @BeforeEach
    void setUp() {
        Json.mapper.enableDefaultTyping();
        this.socket = Mockito.spy(new TcpServerConnection(Mockito.mock(NetSocket.class), new JsonMessageConverter()));
        this.vertx = Vertx.vertx();
        this.automatedDataHandler = new AutomatedDataHandler(socket, true);
        deploy(automatedDataHandler);
        automatedDataHandler.init(vertx, vertx.getOrCreateContext());
    }

    @AfterEach
    void tearDown() {
        undeploy(automatedDataHandler.deploymentID());
    }

    @Test
    void clientSuccessAuth() {
        final var authenticationRequest = Buffer.buffer(Json.encode(new AuthenticationRequest())).appendString(DELIMITER);
        final var clientId = new ClientId("successClientId");
        final var authenticationResult = Buffer.buffer(Json.encode(new AuthenticationResultSuccess(clientId, Collections.emptyList()))).appendString(DELIMITER);
        automatedDataHandler.handle(authenticationRequest);
        Mockito.verify(socket).sendMessage(Mockito.any(AuthenticationResponse.class));
        automatedDataHandler.handle(authenticationResult);
        Mockito.verify(socket, Mockito.timeout(22000).atLeast(10)).sendMessage(Mockito.any(ChatMessage.class));
    }

    @Test
    void clientFailedAuth() {
        final var authenticationRequest = Buffer.buffer(Json.encode(new AuthenticationRequest())).appendString(DELIMITER);
        final var clientId = new ClientId("failedClientId");
        final var authenticationResult = Buffer.buffer(Json.encode(new AuthenticationResultFailure(clientId))).appendString(DELIMITER);
        automatedDataHandler.handle(authenticationRequest);
        Mockito.verify(socket).sendMessage(Mockito.any(AuthenticationResponse.class));
        automatedDataHandler.handle(authenticationResult);
        Mockito.verify(socket, Mockito.timeout(5000).times(2)).sendMessage(Mockito.any(AuthenticationResponse.class));
    }
}