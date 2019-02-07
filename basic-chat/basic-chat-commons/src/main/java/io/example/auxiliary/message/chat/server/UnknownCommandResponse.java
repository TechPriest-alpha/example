package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.server.abstracts.MessageFromServer;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class UnknownCommandResponse extends MessageFromServer {
    private final MessageType messageType = MessageType.UNKNOWN_COMMAND_RESPONSE;
    private final String message;
    private final String unknownCommand;

    public UnknownCommandResponse(final String unknownCommand) {
        this.message = message(messageKey(), unknownCommand);
        this.unknownCommand = unknownCommand;
    }

    @Override
    protected String messageKey() {
        return "unknown.command.response";
    }
}
