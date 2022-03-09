package my.playground.orm.handling;

import jakarta.enterprise.context.ApplicationScoped;
import my.playground.orm.entities.Client;
import my.playground.orm.entities.sub.UserId;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class AggregateCache {
    private final ConcurrentMap<UserId, Client> clients = new ConcurrentHashMap<>();
}
