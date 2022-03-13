package my.playground.orm.firsttry.events;

import my.playground.orm.firsttry.entities.sub.UserId;

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
