package io.example.server.api.tcp.messages;

import io.example.server.api.tcp.TcpServer;
import io.vertx.core.net.NetSocket;

public abstract class TcpClientConnection {
    public void sendToClient(final String data) {
        getClientConnection().write(data + TcpServer.DELIMITER);
    }

    abstract NetSocket getClientConnection();

}
