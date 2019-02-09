package io.example.client.api.server;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.api.server.handling.TcpServerConnection;
import io.example.client.core.DataHandlerFactory;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class ChatTcpClient extends BaseVerticle {
    public static final String DELIMITER = "\r\n";

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
        createClient();
        super.start(startFuture);
    }

    protected void createClient() {
        vertx
            .createNetClient(new NetClientOptions().setConnectTimeout(10_000).setReconnectAttempts(5).setReconnectInterval(1000))
            .connect(serverPort, serverHost, this::onConnect);
    }

    private void onConnect(final AsyncResult<NetSocket> connectionEvent) {
        if (connectionEvent.succeeded()) {
            final var socket = connectionEvent.result();
            final RecordParser recordParser = RecordParser.newDelimited(DELIMITER);
            final var dataHandler = dataHandlerFactory.create(new TcpServerConnection(socket, messageConverter), allowUnknownCommands);
            recordParser.handler(dataHandler);
            vertx.deployVerticle(dataHandler, onComplete -> socket.handler(recordParser));
        } else {
            log.error("Error when connecting", connectionEvent.cause());
        }
    }
}
