package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationRequest implements BaseChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_REQUEST;
    private final String message;
}
