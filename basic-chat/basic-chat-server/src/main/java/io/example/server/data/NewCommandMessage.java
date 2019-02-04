package io.example.server.data;

import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class NewCommandMessage implements BaseInternalMessage {
    private final String clientId;
    private final ChatCommand chatCommand;
}
