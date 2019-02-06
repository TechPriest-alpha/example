package io.example.server.data;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.chat.BaseChatMessage;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class CommandResponse implements BaseInternalMessage {
    private final ClientId clientId;
    private final BaseChatMessage chatMessage;
}
