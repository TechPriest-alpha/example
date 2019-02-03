package io.example.server.data;

import io.vertx.core.net.NetSocket;
import lombok.Value;

@Value
public class ClientInfo {
    private final NetSocket clientConnection;
    private final String clientId;
}
