package io.example.auxiliary.message.chat;

import lombok.Value;
import lombok.experimental.NonFinal;

import java.time.Instant;

@Value
@NonFinal
public class ChatMessage implements BaseChatMessage {
    private final String message;
    private final MessageType messageType = MessageType.CHAT_TEXT;
    private final Instant messageTime = Instant.now();
}
