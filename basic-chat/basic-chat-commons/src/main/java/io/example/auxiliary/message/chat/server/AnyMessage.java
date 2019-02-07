package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.example.auxiliary.message.chat.server.abstracts.MessageFromServer;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(value = {"messageType"}, ignoreUnknown = true)
public class AnyMessage extends MessageFromServer {
    private final MessageType messageType = MessageType.ANY;
    private final String message;

    @ConstructorProperties({"message"})
    public AnyMessage(final String message) {
        this.message = message;
    }

    @Override
    @JsonIgnore
    protected String messageKey() {
        return "";
    }
}
