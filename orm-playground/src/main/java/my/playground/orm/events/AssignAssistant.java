package my.playground.orm.events;

import my.playground.orm.entities.sub.UserId;

public record AssignAssistant(Long clientId,
                              UserId assistant) implements ClientEvent {
}
