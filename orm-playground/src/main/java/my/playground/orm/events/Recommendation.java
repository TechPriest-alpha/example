package my.playground.orm.events;

import my.playground.orm.domain.Grade;

public record Recommendation(Long clientId,
                             Long assistanceId, String comment,
                             Grade grade) implements ClientEvent {
}
