package io.example.server.data;

import io.example.auxiliary.message.chat.client.ChatMessage;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class NewChatMessage implements BaseInternalMessage {
    private final String clientId;
    private final ChatMessage chatMessage;
}
