package io.example.client.api;

import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.conversion.JsonMessageConverter;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

class DataHandlerTest {
    private DataHandler dataHandler;
    private ServerConnection socket;
    private Vertx vertx;

    @BeforeEach
    void setUp() {
        Json.mapper.enableDefaultTyping();
        this.socket = Mockito.spy(new ServerConnection(Mockito.mock(NetSocket.class), new JsonMessageConverter()));
        this.vertx = Vertx.vertx();
        this.dataHandler = new DataHandler(socket, Vertx.vertx());
    }

    @AfterEach
    void tearDown() {
        vertx.close();
    }

    @Test
    void clientSuccessAuth() {
        final var authenticationRequest = Buffer.buffer(Json.encode(new AuthenticationRequest("authentication request")));
        final var authenticationResult = Buffer.buffer(Json.encode(new AuthenticationResult("good", AuthVerdict.SUCCESS, Collections.emptyList())));
        dataHandler.handle(authenticationRequest);
        Mockito.verify(socket).sendMessage(Mockito.any(AuthenticationResponse.class));
        dataHandler.handle(authenticationResult);
        Mockito.verify(socket, Mockito.timeout(22000).times(10)).sendMessage(Mockito.any(ChatMessage.class));
    }

    @Test
    void clientFailedAuth() {
        final var authenticationRequest = Buffer.buffer(Json.encode(new AuthenticationRequest("authentication request")));
        final var authenticationResult = Buffer.buffer(Json.encode(new AuthenticationResult("bad", AuthVerdict.NAME_ALREADY_REGISTERED, Collections.emptyList())));
        dataHandler.handle(authenticationRequest);
        Mockito.verify(socket).sendMessage(Mockito.any(AuthenticationResponse.class));
        dataHandler.handle(authenticationResult);
        Mockito.verify(socket, Mockito.timeout(5000).times(2)).sendMessage(Mockito.any(AuthenticationResponse.class));
    }
}