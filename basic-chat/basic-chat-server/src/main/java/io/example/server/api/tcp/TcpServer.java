package io.example.server.api.tcp;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.SpringVerticle;
import io.vertx.core.Future;
import io.vertx.core.net.NetServerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@SpringVerticle
public class TcpServer extends BaseVerticle {

    private final Integer port;

    @Autowired
    public TcpServer(@Value("${chat.server.port}") final Integer port) {
        this.port = port;
    }

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        final var serverOptions = new NetServerOptions().setPort(port);
        vertx.createNetServer(serverOptions).connectHandler(new ServerTcpConnectionHandler()).listen();
        super.start(startFuture);
    }
}
