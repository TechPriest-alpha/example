package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.vertx.core.net.NetSocket;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectedClientManager extends BaseVerticle {
    private final NetSocket clientConnection;
}
