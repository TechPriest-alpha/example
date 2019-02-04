package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AuthenticationResponse extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESPONSE;

    @ConstructorProperties("message")
    public AuthenticationResponse(final String message) {
        super(message);
    }

    @JsonIgnore
    public String getClientId() {
        return getMessage();
    }
}
