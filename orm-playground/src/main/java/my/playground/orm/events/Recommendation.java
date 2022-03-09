package my.playground.orm.events;

import my.playground.orm.domain.Grade;
import my.playground.orm.entities.sub.UserId;

public record Recommendation(UserId clientId,
                             UserId assistantId, String comment,
                             Grade grade) implements ClientEvent {
}
