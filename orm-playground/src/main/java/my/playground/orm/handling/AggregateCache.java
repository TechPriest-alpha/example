package my.playground.orm.handling;

import jakarta.enterprise.context.ApplicationScoped;
import my.playground.orm.entities.Client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class AggregateCache {
    private final ConcurrentMap<Long, Client> clients = new ConcurrentHashMap<>();

    public Client get(final Long clientId) {
        return clientId == null ? null : clients.get(clientId);
    }

    public void put(final Long clientId, final Client client) {
        clients.put(clientId, client);
    }
}
