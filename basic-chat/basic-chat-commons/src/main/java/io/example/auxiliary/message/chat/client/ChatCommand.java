package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.client.abstracts.ClientChatMessage;
import io.example.auxiliary.message.chat.types.CommandType;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ChatCommand extends ClientChatMessage {
    private final MessageType messageType = MessageType.COMMAND;
    private final CommandType commandType;
    private final Instant messageTime = Instant.now();
}
