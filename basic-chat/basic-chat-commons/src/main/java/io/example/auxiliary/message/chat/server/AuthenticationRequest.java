package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.server.abstracts.MessageFromServer;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationRequest extends MessageFromServer {
    private final MessageType messageType = MessageType.AUTHENTICATION_REQUEST;
    private final String message;

    public AuthenticationRequest() {
        this.message = message();
    }

    @ConstructorProperties({"message"})
    private AuthenticationRequest(final String message) {
        this.message = message;
    }

    @Override
    protected String messageKey() {
        return "authentication.request";
    }
}
