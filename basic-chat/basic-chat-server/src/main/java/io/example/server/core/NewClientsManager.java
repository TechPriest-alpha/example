package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.AuthResponse;
import io.example.server.Routing;
import io.example.server.data.NewClient;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringVerticle
public class NewClientsManager extends BaseVerticle {
    private static final Logger log = LoggerFactory.getLogger(NewClientsManager.class);

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.NEW_CLIENTS_MANAGER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void clientRequestsHandler(final NewClient newClient) {
        log.info("Running authentication for new client");
        newClient.authenticationHandler(new Authenticator(vertx, newClient));
        newClient.sendAuthenticationRequest("Please introduce yourself");
        log.info("Authentication request sent");
    }

    @RequiredArgsConstructor
    private static class Authenticator implements Handler<Buffer> {
        private final Vertx vertx;
        private final NewClient authenticatingClient;

        @Override
        public void handle(final Buffer event) {
            final var authResponse = authenticatingClient.decode(event, AuthResponse.class);
            log.info("Client response to auth request: {}", authResponse);
            vertx.deployVerticle(new ConnectedClientManager(authenticatingClient.toAuthenticatedClient(authResponse)));
        }
    }
}
