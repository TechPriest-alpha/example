package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationRequest extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_REQUEST;

    @ConstructorProperties("message")
    public AuthenticationRequest(final String message) {
        super(message);
    }
}
