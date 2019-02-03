package io.example.auxiliary.message.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESPONSE;

    @ConstructorProperties("message")
    public AuthResponse(final String message) {
        super(message);
    }

    @JsonIgnore
    public String getClientId() {
        return getMessage();
    }
}
