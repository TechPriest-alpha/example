package io.example.auxiliary.message.chat.server;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.chat.types.AuthVerdict;
import io.example.auxiliary.message.chat.types.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@RequiredArgsConstructor
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class AuthenticationResult implements BaseChatMessage {
    private final MessageType messageType = MessageType.AUTHENTICATION_RESULT;
    private final String message;
    private final AuthVerdict verdict;
    private final String clientId;
    private final List<ChatMessage> lastMessages;

    public AuthenticationResult(final String message, final AuthVerdict verdict, final String clientId) {
        this(message, verdict, clientId, Collections.emptyList());
    }
}
