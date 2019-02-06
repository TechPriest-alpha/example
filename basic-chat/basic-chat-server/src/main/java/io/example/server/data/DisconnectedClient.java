package io.example.server.data;

import io.example.auxiliary.message.ClientId;
import io.example.auxiliary.message.internal.BaseInternalMessage;
import lombok.Value;

@Value
public class DisconnectedClient implements BaseInternalMessage {
    private final ClientId clientId;
}
