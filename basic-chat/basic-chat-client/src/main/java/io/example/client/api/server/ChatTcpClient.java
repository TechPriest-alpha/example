package io.example.client.api.server;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.api.server.handling.ServerConnection;
import io.example.client.core.DataHandlerFactory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle(instances = 1)
public class ChatTcpClient extends BaseVerticle {

    private final MessageConverter messageConverter;
    private final String serverHost;
    private final int serverPort;
    private final boolean allowUnknownCommands;
    private final DataHandlerFactory dataHandlerFactory;

    @Autowired
    public ChatTcpClient(
        final MessageConverter messageConverter,
        @Value("${server.host}") final String serverHost,
        @Value("${server.port}") final int serverPort,
        @Value("${allow.unknown.commands:true}") final boolean allowUnknownCommands,
        final DataHandlerFactory dataHandlerFactory
    ) {
        this.messageConverter = messageConverter;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.allowUnknownCommands = allowUnknownCommands;
        this.dataHandlerFactory = dataHandlerFactory;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        vertx
            .createNetClient(new NetClientOptions().setConnectTimeout(10_000).setReconnectAttempts(5).setReconnectInterval(1000))
            .connect(serverPort, serverHost, this::onConnect);
        super.start(startFuture);
    }

    private void onConnect(final AsyncResult<NetSocket> connectionEvent) {
        if (connectionEvent.succeeded()) {
            final var socket = connectionEvent.result();
            final var dataHandler = dataHandlerFactory.create(new ServerConnection(socket, messageConverter), allowUnknownCommands);
            vertx.deployVerticle(dataHandler);
            socket.handler(dataHandler);
        } else {
            log.error("Error when connecting", connectionEvent.cause());
        }
    }
}
