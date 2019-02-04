package io.example.auxiliary.message.chat.conversion;

import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import io.vertx.core.buffer.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonMessageConverterTest {
    private JsonMessageConverter converter;

    @BeforeEach
    void setUp() {
        this.converter = new JsonMessageConverter();
    }

    @Test
    void authRequestEncode_Decode() {
        final var authRequest = new AuthenticationRequest("auth request");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, AuthenticationRequest.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, AuthenticationRequest.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }

    @Test
    void authResponseEncode_Decode() {
        final var authRequest = new AuthenticationResponse("auth response");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, AuthenticationResponse.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, AuthenticationResponse.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }

    @Test
    void basicMessageEncode_Decode() {
        final var authRequest = new ChatMessage("auth request", "testClient");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, ChatMessage.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, ChatMessage.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }
}