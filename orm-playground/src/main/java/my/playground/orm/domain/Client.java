package my.playground.orm.domain;

import lombok.RequiredArgsConstructor;
import my.playground.orm.domain.errors.AssistantAlreadyAssignedError;
import my.playground.orm.domain.errors.AssistantSameAsClientError;
import my.playground.orm.domain.errors.NoAssignedAssistantError;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.AssistantEntity;
import my.playground.orm.entities.sub.UserId;
import my.playground.orm.events.Recommendation;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class Client {
    private final UserEntity user;
    private AtomicReference<AssistantEntity> currentAssistant = new AtomicReference<>();

    public UserId id() {
        return user.getId();
    }

    public void assignAssistant(final AssistantEntity assistant) {
        if (Objects.equals(assistant.getId(), user.getId())) {
            throw new AssistantSameAsClientError(user);
        }
        if (!this.currentAssistant.compareAndSet(null, assistant)) {
            throw new AssistantAlreadyAssignedError(user, currentAssistant.get(), assistant);
        }
    }

    //TODO: concurrent update
    public void rateAssistant(final Recommendation recommendation) {
        if (currentAssistant.get() == null) {
            throw new NoAssignedAssistantError(user);
        }
        currentAssistant.get().addRecommendation(recommendation);
    }
}
