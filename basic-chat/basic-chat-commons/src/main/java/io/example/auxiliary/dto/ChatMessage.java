package io.example.auxiliary.dto;

import lombok.Value;

import java.time.Instant;

@Value
public class ChatMessage {
    private final String message;
    private final Instant messageTime = Instant.now();
}
