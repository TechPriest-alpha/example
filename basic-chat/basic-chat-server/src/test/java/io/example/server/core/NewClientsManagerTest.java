package io.example.server.core;

import io.example.auxiliary.message.chat.server.AuthenticationRequest;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NewClientsManagerTest {
    @Test
    void testMessageRetrieval() {
        final var bundle = ResourceBundle.getBundle("messages/CommonMessage");
        assertNotNull(bundle);
        assertEquals(new AuthenticationRequest().getMessage(), "Please introduce yourself");
    }
}