package io.example.server.data;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.client.ChatCommand;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class NewCommandMessage implements BaseInternalMessage {
    private final ClientId clientId;
    private final ChatCommand chatCommand;
}
