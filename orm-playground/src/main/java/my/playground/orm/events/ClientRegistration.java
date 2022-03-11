package my.playground.orm.events;

import my.playground.orm.entities.sub.UserId;

public record ClientRegistration(UserId userId, String name) implements ClientEvent {
    public ClientRegistration(final String name) {
        this(null, name);
    }

    @Override
    public Long clientId() {
        return null;
    }

    public boolean newUser() {
        return userId == null;
    }
}
