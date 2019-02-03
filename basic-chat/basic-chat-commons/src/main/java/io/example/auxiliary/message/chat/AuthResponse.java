package io.example.auxiliary.message.chat;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESPONSE;

    public AuthResponse(final String message) {
        super(message);
    }
}
