package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ChatMessage implements BaseChatMessage {
    private final String message;
    private final MessageType messageType = MessageType.CHAT_TEXT;
    private final Instant messageTime = Instant.now();
}
