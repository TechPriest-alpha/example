package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ChatCommand implements BaseChatMessage {
    private final MessageType messageType = MessageType.COMMAND;
    private final CommandType commandType;
    private final Instant messageTime = Instant.now();
}
