package my.playground.orm.firsttry.handling;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import my.playground.orm.firsttry.entities.Client;
import my.playground.orm.firsttry.events.entity.FromClient;

@ApplicationScoped
public class ClientFactory {
    @Produces
    @Named
    @Dependent
    public Client client(final Event<FromClient> eventPublisher) {
        return new Client(eventPublisher);
    }
}
