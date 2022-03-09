package my.playground.orm.handling;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.playground.orm.BasicOperation;
import my.playground.orm.entities.Client;
import my.playground.orm.entities.sub.UserId;
import my.playground.orm.events.AssistantEvent;
import my.playground.orm.events.ClientEvent;
import my.playground.orm.events.ClientRegistration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class EventHandler {
    private final BasicOperation basicOperation;
    private final ConcurrentMap<UserId, Client> clients = new ConcurrentHashMap<>();

    public <T extends ClientEvent> void processClientEventAsync(@ObservesAsync final T event) {
        processClientEvent(event);
    }

    public <T extends ClientEvent> void processClientEvent(@Observes final T event) {
        Client client;
        if (event.newClient()) {
            client = createClient((ClientRegistration) event);
            clients.put(client.id(), client);
            log.info("New client: {}", event.clientId());
        } else {
            final UserId clientId = event.clientId();
            client = clients.get(clientId);
            if (client == null) {
                client = basicOperation.findClient(clientId);
                client.apply(event);
                clients.put(clientId, client);
                log.info("Existing client not in cache: {}", event.clientId());
            } else {
                client.apply(event);
                log.info("Existing client in cache: {}", event.clientId());
            }
        }
        basicOperation.write(client);
    }

    private Client createClient(final ClientRegistration event) {
        final var client = new Client();
        client.initFrom(event);
        basicOperation.write(client);
        return client;
    }

    private <T extends ClientEvent> Client handleClientUpdate(final T event, final Client client) {
        return null;
    }

    public <T extends AssistantEvent> void processAssistantEvent(final T event) {

    }
}
