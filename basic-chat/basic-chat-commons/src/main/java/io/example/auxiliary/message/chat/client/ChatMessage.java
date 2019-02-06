package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.abstracts.ClientChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ChatMessage extends ClientChatMessage {
    private final String message;
    private final ClientId clientId;
    private final MessageType messageType = MessageType.CHAT_TEXT;
    private final Instant messageTime = Instant.now();

}
