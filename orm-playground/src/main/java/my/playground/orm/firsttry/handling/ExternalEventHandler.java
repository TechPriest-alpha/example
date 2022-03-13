package my.playground.orm.firsttry.handling;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.playground.orm.firsttry.OrmOperations;
import my.playground.orm.firsttry.entities.Client;
import my.playground.orm.firsttry.entities.UserEntity;
import my.playground.orm.firsttry.events.AssistantEvent;
import my.playground.orm.firsttry.events.ClientEvent;
import my.playground.orm.firsttry.events.ClientRegistration;
import org.jboss.weld.environment.se.WeldContainer;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExternalEventHandler {
    private final OrmOperations ormOperations;
    private final AggregateCache clients;

    public <T extends ClientEvent> void processClientEventAsync(@ObservesAsync final T event, final WeldContainer weldContainer) {
        processClientEvent(event, weldContainer);
    }

    public <T extends ClientEvent> void processClientEvent(@Observes final T event, final WeldContainer weldContainer) {
        final Long clientId = event.clientId();
        var client = clients.get(clientId);
        if (client == null) {

            client = weldContainer.select(Client.class).get();
            client.setClientId(clientId);
            client = ormOperations.findClient(client);
            client.apply(event);
            clients.put(clientId, client);
            log.info("Existing client not in cache: {}", client.getClientId());
        } else {
            client.apply(event);
            log.info("Existing client in cache: {}", client.getClientId());
        }

        ormOperations.write(client);
    }

    public void createClientAsync(@ObservesAsync final ClientRegistration event, final Client client) {
        createClient(event, client);
    }

    public void createClient(@Observes final ClientRegistration event, final Client client) {
        var user = event.newUser() ? new UserEntity(event.name()) : ormOperations.readUser(event.userId());
        client.initFrom(user, event);
//        basicOperation.write(client);
//        clients.put(client.getClientId(), client);
        log.info("New client created");
    }

    private <T extends ClientEvent> Client handleClientUpdate(final T event, final Client client) {
        return null;
    }

    public <T extends AssistantEvent> void processAssistantEvent(final T event) {
    }
}
