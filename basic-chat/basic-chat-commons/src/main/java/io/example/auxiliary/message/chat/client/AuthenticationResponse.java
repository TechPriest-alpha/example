package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(callSuper = true)
public class AuthenticationResponse implements BaseChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESPONSE;
    private final String message;

    @JsonIgnore
    public String getClientId() {
        return getMessage();
    }
}
