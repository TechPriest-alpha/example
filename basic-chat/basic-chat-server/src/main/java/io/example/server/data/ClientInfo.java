package io.example.server.data;

import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.Value;

@Value
public class ClientInfo implements ClientConnectionCommons {
    private final NetSocket clientConnection;
    private final String clientId;
    private final MessageConverter messageConverter;


    public void chatHandler(final Handler<Buffer> chatHandler) {
        clientConnection.handler(chatHandler);
    }

    public void disconnectHandler(final Handler<Void> disconnectHandler) {
        clientConnection.closeHandler(disconnectHandler);
    }
}
