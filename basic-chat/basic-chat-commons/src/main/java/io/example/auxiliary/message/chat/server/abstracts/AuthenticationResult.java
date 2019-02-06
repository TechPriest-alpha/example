package io.example.auxiliary.message.chat.server.abstracts;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public abstract class AuthenticationResult extends MessageFromServer {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESULT;
    private final String message;
    private final ClientId clientId;
    private final AuthVerdict verdict;
    private final List<ChatMessage> lastMessages;

    public AuthenticationResult(final ClientId clientId, final AuthVerdict verdict, final List<ChatMessage> lastMessages) {
        this.message = message(messageKey(), clientId.getValue());
        this.clientId = clientId;
        this.verdict = verdict;
        this.lastMessages = lastMessages;
    }
}
