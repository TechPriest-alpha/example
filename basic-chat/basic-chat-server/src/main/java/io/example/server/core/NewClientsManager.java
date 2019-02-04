package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.server.AuthenticationResult;
import io.example.auxiliary.message.chat.types.AuthVerdict;
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
public class NewClientsManager extends BaseVerticle {
    private static final Logger log = LoggerFactory.getLogger(NewClientsManager.class);

    private final MessageStorage messageStorage;

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.NEW_CLIENTS_MANAGER);
        super.start(startFuture);
    }

    @HandlerMethod
    public void clientRequestsHandler(final NewClient newClient) {
        log.debug("Running authentication for new client");
        newClient.authenticationHandler(new Authenticator(vertx, newClient));
        newClient.sendAuthenticationRequest("Please introduce yourself");
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
            if (messageStorage.containsClient(clientId)) {
                authenticatingClient.sendToClient(new AuthenticationResult("Name already in use", AuthVerdict.NAME_ALREADY_REGISTERED, clientId));
                log.info("Client response to auth request: {} is already in use", authResponse);
            } else {
                messageStorage.addClient(clientId);
                authenticatingClient.sendToClient(new AuthenticationResult("Welcome " + clientId, AuthVerdict.SUCCESS, clientId, Collections.emptyList()));
                vertx.deployVerticle(new AuthenticatedClientManager(authenticatingClient.toAuthenticatedClient(authResponse)));
                log.debug("Client response to auth request: {} registered as new client", authResponse);
            }
        }
    }
}
