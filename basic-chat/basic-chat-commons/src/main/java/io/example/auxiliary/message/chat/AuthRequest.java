package io.example.auxiliary.message.chat;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthRequest extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_REQUEST;

    @ConstructorProperties("message")
    public AuthRequest(final String message) {
        super(message);
    }
}
