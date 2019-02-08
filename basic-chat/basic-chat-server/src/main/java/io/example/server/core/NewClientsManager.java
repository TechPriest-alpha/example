package io.example.server.core;

import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.auxiliary.message.chat.client.AuthenticationResponse;
import io.example.auxiliary.message.chat.server.AuthenticationResultFailure;
import io.example.auxiliary.message.chat.server.AuthenticationResultSuccess;
import io.example.server.BaseServerVerticle;
import io.example.server.Routing;
import io.example.server.api.tcp.ServerSocketHandler;
import io.example.server.contracts.NewClientContract;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void clientRequestsHandler(final NewClientContract newClient) {
        log.debug("Running authentication for new client");
        newClient.authenticationHandler(new Authenticator(this, newClient));
        newClient.sendAuthenticationRequest();
        log.debug("Authentication request sent");
    }

    @RequiredArgsConstructor
    public static class Authenticator extends ServerSocketHandler {
        private final NewClientsManager newClientsManager;
        private final NewClientContract authenticatingClient;

        @Override
        public void doHandle(final Buffer event) {
            final var clientResponse = authenticatingClient.decode(event);
            if (!clientResponse.getMessageType().isAuthenticationResponse()) {
                authenticatingClient.sendAuthenticationRequest();
                log.info("Wrong message from client: {}", event.toString());
                return;
            }
            final var authResponse = (AuthenticationResponse) clientResponse;
            final var clientId = authResponse.getClientId();
            if (newClientsManager.dataStorage.containsClient(clientId)) {
                authenticatingClient.sendToClient(new AuthenticationResultFailure(clientId));
                log.info("Client response to auth request: {} is already in use", authResponse);
            } else {
                newClientsManager.dataStorage.addClient(clientId);
                final var lastMessages = newClientsManager.dataStorage.lastMessages();
                newClientsManager.vertx.deployVerticle(
                    new AuthenticatedClientManager(authenticatingClient.toAuthenticatedClient(authResponse)),
                    onComplete -> authenticatingClient.sendToClient(new AuthenticationResultSuccess(clientId, lastMessages))
                );
                log.debug("Client response to auth request: {} registered as new client", authResponse);
            }
        }
    }
}
