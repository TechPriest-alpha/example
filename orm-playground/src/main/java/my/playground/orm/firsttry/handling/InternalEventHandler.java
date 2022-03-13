package my.playground.orm.firsttry.handling;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.playground.orm.firsttry.OrmOperations;
import my.playground.orm.firsttry.events.entity.CreateUser;
import my.playground.orm.firsttry.events.entity.SaveClient;
import org.jboss.weld.environment.se.WeldContainer;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class InternalEventHandler {
    private final OrmOperations ormOperations;
    private final AggregateCache clients;

    public void processClientEvent(@Observes final CreateUser event, final WeldContainer weldContainer) {
        if (!ormOperations.entityExists(event.user())) {
            final var user = ormOperations.write(event.user());
            log.info("User created: {}", user);
        } else {
            log.info("User creation not required: {}", event.user());
        }
    }

    public void processClientEvent(@Observes final SaveClient event, final WeldContainer weldContainer) {
        final var client = ormOperations.write(event.client());
        clients.put(client.getClientId(), client);
        log.info("Client created: {}", client);
    }

}
