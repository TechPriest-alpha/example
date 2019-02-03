package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationResult extends ChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESULT;

    private final AuthVerdict verdict;
    private final List<ChatMessage> lastMessages;

    @ConstructorProperties({"message", "verdict", "lastMessages"})
    public AuthenticationResult(final String message, final AuthVerdict verdict, final List<ChatMessage> lastMessages) {
        super(message);
        this.verdict = verdict;
        this.lastMessages = lastMessages;
    }
}
