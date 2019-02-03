package io.example.auxiliary.message.chat.conversion;

import io.example.auxiliary.message.chat.AuthRequest;
import io.example.auxiliary.message.chat.AuthResponse;
import io.example.auxiliary.message.chat.ChatMessage;
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
        final var authRequest = new AuthRequest("auth request");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, AuthRequest.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, AuthRequest.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }

    @Test
    void authResponseEncode_Decode() {
        final var authRequest = new AuthResponse("auth response");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, AuthResponse.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, AuthResponse.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }

    @Test
    void basicMessageEncode_Decode() {
        final var authRequest = new ChatMessage("auth request");
        final var encodedString = converter.encode(authRequest);
        final var decodedFromString = converter.decode(encodedString, ChatMessage.class);
        final var encodedBuffer = Buffer.buffer(encodedString);
        final var decodedFromBuffer = converter.decode(encodedBuffer, ChatMessage.class);
        assertEquals(authRequest, decodedFromString);
        assertEquals(authRequest, decodedFromBuffer);
    }
}