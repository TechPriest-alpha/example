package io.example.server.api.tcp;

import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.conversion.MessageConverter;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.data.NewClient;
import io.vertx.core.Future;
import io.vertx.core.net.NetServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle(instances = 100)
public class TcpServer extends BaseServerVerticle {
    private static final Logger log = LoggerFactory.getLogger(TcpServer.class);

    private final Integer port;
    private final MessageConverter messageConverter;

    @Autowired
    public TcpServer(@Value("${chat.server.port}") final Integer port, final MessageConverter messageConverter) {
        this.port = port;
        this.messageConverter = messageConverter;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        final var serverOptions = new NetServerOptions().setPort(port);
        vertx.createNetServer(serverOptions)
            .connectHandler(newConnection -> {
                sendMessageLocally(Routing.NEW_CLIENTS_MANAGER, new NewClient(newConnection, messageConverter));
                log.debug("New connection received form: {}", newConnection.remoteAddress());
            })
            .listen();
        super.start(startFuture);
    }
}
