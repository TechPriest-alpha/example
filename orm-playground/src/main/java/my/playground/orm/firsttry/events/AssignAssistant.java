package my.playground.orm.firsttry.events;

import my.playground.orm.firsttry.entities.sub.UserId;

public record AssignAssistant(Long clientId,
                              UserId assistant) implements ClientEvent {
}
