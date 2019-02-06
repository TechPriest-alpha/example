package io.example.auxiliary.message.chat.client;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
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
public class UnknownChatCommand extends ChatCommand {
    private final MessageType messageType = MessageType.COMMAND;
    private final CommandType commandType = CommandType.UNKNOWN;
    private final Instant messageTime = Instant.now();
    private final String command;
    private final ClientId clientId;

    public UnknownChatCommand(final String command, final ClientId clientId) {
        super(clientId, CommandType.UNKNOWN);
        this.command = command;
        this.clientId = clientId;
    }
}
