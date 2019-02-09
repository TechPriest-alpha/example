package io.example.client.api;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.client.core.DataHandlerFactory;
import io.vertx.core.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle
public class AutomatedChatTest extends BaseVerticle {
    private final MessageConverter messageConverter;
    private final String serverHost;
    private final int serverPort;
    private final boolean allowUnknownCommands;
    private final DataHandlerFactory dataHandlerFactory;
    private final int clientInstances;

    @Autowired
    public AutomatedChatTest(
        final MessageConverter messageConverter,
        @Value("${server.host}") final String serverHost,
        @Value("${server.port}") final int serverPort,
        @Value("${allow.unknown.commands:true}") final boolean allowUnknownCommands,
        final DataHandlerFactory dataHandlerFactory,
        @Value("${client.instances:1}") final int clientInstances
    ) {
        this.messageConverter = messageConverter;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.allowUnknownCommands = allowUnknownCommands;
        this.dataHandlerFactory = dataHandlerFactory;
        this.clientInstances = clientInstances;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        for (int i = 0; i < clientInstances; i++) {
            vertx.deployVerticle(
                new AutomatedTcpChatClient(messageConverter, serverHost, serverPort, allowUnknownCommands, dataHandlerFactory)
            );
        }
        super.start(startFuture);
    }
}
