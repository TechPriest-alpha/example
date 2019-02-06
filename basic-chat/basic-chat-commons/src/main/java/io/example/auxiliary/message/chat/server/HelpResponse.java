package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.server.abstracts.MessageFromServer;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class HelpResponse extends MessageFromServer {
    private final MessageType messageType = MessageType.HELP_RESPONSE;
    private final String message;

    public HelpResponse() {
        this.message = message();
    }

    @Override
    protected String messageKey() {
        return "help.response";
    }
}
