package my.playground.orm.events;

import my.playground.orm.entities.sub.UserId;

public record ClientRegistration(String name) implements ClientEvent {
    @Override
    public UserId clientId() {
        return null;
    }
}
