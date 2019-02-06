package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.data.NewClient;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@SpringVerticle(instances = 10)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class NewClientsManager extends BaseServerVerticle {
    private static final Logger log = LoggerFactory.getLogger(NewClientsManager.class);

    private final DataStorage dataStorage;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.NEW_CLIENTS_MANAGER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void clientRequestsHandler(final NewClient newClient) {
        log.debug("Running authentication for new client");
        newClient.authenticationHandler(new Authenticator(vertx, newClient));
        newClient.sendAuthenticationRequest();
        log.debug("Authentication request sent");
    }

    @RequiredArgsConstructor
    private class Authenticator implements Handler<Buffer> {
        private final Vertx vertx;
        private final NewClient authenticatingClient;

        @Override
        public void handle(final Buffer event) {
            final var authResponse = authenticatingClient.decode(event, AuthenticationResponse.class);

            final var clientId = authResponse.getClientId();
            if (dataStorage.containsClient(clientId)) {
                authenticatingClient.sendToClient(new AuthenticationResultFailure(clientId));
                log.info("Client response to auth request: {} is already in use", authResponse);
            } else {
                dataStorage.addClient(clientId);
                authenticatingClient.sendToClient(new AuthenticationResultSuccess(clientId, Collections.emptyList()));
                vertx.deployVerticle(new AuthenticatedClientManager(authenticatingClient.toAuthenticatedClient(authResponse)));
                log.debug("Client response to auth request: {} registered as new client", authResponse);
            }
        }
    }
}
