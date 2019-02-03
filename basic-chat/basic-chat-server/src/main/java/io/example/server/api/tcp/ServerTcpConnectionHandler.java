package io.example.server.api.tcp;

import io.example.auxiliary.dto.ChatMessage;
import io.vertx.core.Handler;
import io.vertx.core.json.Json;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerTcpConnectionHandler implements Handler<NetSocket> {
    private static final Logger log = LoggerFactory.getLogger(ServerTcpConnectionHandler.class);

    @Override
    public void handle(final NetSocket netSocket) {
        netSocket.handler(new DataHandler());
        netSocket.write(Json.encode(new ChatMessage("Hello from Server")));
        log.info("Client {} connected", "clientId");
    }
}
