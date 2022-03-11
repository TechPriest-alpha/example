package my.playground.orm.events;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ClientEvent {

    ObjectMapper om = new ObjectMapper().findAndRegisterModules();

    Long clientId();

    default boolean newClient() {
        return clientId() == null;
    }

    default String toRawValue() {
        try {
            return om.writeValueAsString(this);
        } catch (final Exception ex) {
            throw new RuntimeException("Can not serialize event", ex);
        }
    }
}
