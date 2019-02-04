package io.example.server.core;

import io.example.auxiliary.BaseVerticle;
import io.example.auxiliary.annotations.HandlerMethod;
import io.example.auxiliary.annotations.SpringVerticle;
import io.example.server.Routing;
import io.example.server.data.DisconnectedClient;
import io.example.server.data.NewChatMessage;
import io.vertx.core.Future;
import io.vertx.core.impl.ConcurrentHashSet;

@SpringVerticle(instances = 1)
public class MessageStorage extends BaseVerticle {
    private final ConcurrentHashSet<String> REGISTERED_CLIENTS = new ConcurrentHashSet<>();

    @Override
    public void start(final Future<Void> startFuture) throws Exception {
        registerConsumer(Routing.MESSAGE_STORAGE);
        super.start(startFuture);
    }

    @HandlerMethod
    public void onNewMessage(final NewChatMessage newChatMessage) {
        log.debug("New message from {} stored", newChatMessage.getClientId());
    }

    @HandlerMethod
    public void onDisconnectingClient(final DisconnectedClient disconnectedClient) {
        REGISTERED_CLIENTS.remove(disconnectedClient.getClientId());
    }

    public boolean containsClient(final String clientId) {
        return REGISTERED_CLIENTS.contains(clientId);
    }

    public void addClient(final String clientId) {
        REGISTERED_CLIENTS.add(clientId);
    }
}
