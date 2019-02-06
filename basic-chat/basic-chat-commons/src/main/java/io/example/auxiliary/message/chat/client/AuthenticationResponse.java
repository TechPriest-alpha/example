package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.abstracts.ClientChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationResponse extends ClientChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESPONSE;
    private final ClientId clientId;
}
