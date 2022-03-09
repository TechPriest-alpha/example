package my.playground.orm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import my.playground.orm.events.ClientEvent;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TryEvents {
    private final Event<ClientEvent> clientEvent;
    public void send(final ClientEvent event) {
        clientEvent.fire(event);
    }
}
