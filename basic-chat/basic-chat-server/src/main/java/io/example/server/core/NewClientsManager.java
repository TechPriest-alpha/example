package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.server.Routing;
import io.example.server.api.tcp.ServerTcpConnectionHandler;
import io.example.server.data.NewClient;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringVerticle
public class NewClientsManager extends BaseVerticle {
    private static final Logger log = LoggerFactory.getLogger(ServerTcpConnectionHandler.class);

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.NEW_CLIENTS_MANAGER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void clientRequestsHandler(final NewClient newClient) {
        log.info("Running authentication for new client");
        newClient.authenticationHandler(new Authenticator());
        newClient.sendAuthenticationRequest("Please introduce yourself");
        log.info("Authentication request sent");
    }

    private static class Authenticator implements Handler<Buffer> {

        @Override
        public void handle(final Buffer event) {
            log.info("Client response to auth request: {}", event.toString());
        }
    }
}
