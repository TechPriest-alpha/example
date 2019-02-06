package io.example.client.api.server;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.api.server.handling.ChatClientHandler;
import io.vertx.core.Future;
import io.vertx.core.net.NetClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle(instances = 1)
public class ChatTcpClient extends BaseVerticle {

    private final MessageConverter messageConverter;
    private final String serverHost;
    private final int serverPort;
    private final boolean allowUnknownCommands;

    @Autowired
    public ChatTcpClient(
        final MessageConverter messageConverter,
        @Value("${server.host}") final String serverHost,
        @Value("${server.port}") final int serverPort,
        @Value("${allow.unknown.commands:true}") final boolean allowUnknownCommands
    ) {
        this.messageConverter = messageConverter;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.allowUnknownCommands = allowUnknownCommands;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        vertx
            .createNetClient(new NetClientOptions().setConnectTimeout(10_000).setReconnectAttempts(5).setReconnectInterval(1000))
            .connect(serverPort, serverHost, new ChatClientHandler(vertx, messageConverter, allowUnknownCommands));
        super.start(startFuture);
    }
}
