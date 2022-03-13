package my.playground.orm.firsttry.events;

import my.playground.orm.firsttry.domain.Grade;

public record Recommendation(Long clientId,
                             Long assistanceId, String comment,
                             Grade grade) implements ClientEvent {
}
