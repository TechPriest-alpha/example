package io.example.client.messages;

import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class UserInputMessage implements BaseInternalMessage {
    private final String userInput;
}
