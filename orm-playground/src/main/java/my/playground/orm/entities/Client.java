package my.playground.orm.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import my.playground.orm.domain.errors.AssistantAlreadyAssignedError;
import my.playground.orm.domain.errors.AssistantSameAsClientError;
import my.playground.orm.domain.errors.NoAssignedAssistantError;
import my.playground.orm.entities.sub.AssistantEntity;
import my.playground.orm.entities.sub.DomainEvent;
import my.playground.orm.entities.sub.UserId;
import my.playground.orm.events.ClientEvent;
import my.playground.orm.events.ClientRegistration;
import my.playground.orm.events.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@EqualsAndHashCode(of = "user")
public class Client {
    @Id
    private Long clientId;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private AssistantEntity currentAssistant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DomainEvent> eventHistory;

    @Version
    private Long version;

    public void assignAssistant(final AssistantEntity assistant) {
        if (Objects.equals(assistant.getId(), user.getId())) {
            throw new AssistantSameAsClientError(user);
        }
        if (this.currentAssistant != null) {
            throw new AssistantAlreadyAssignedError(user, currentAssistant, assistant);
        }
    }

    //TODO: concurrent update
    public void rateAssistant(final Recommendation recommendation) {
        if (currentAssistant == null) {
            throw new NoAssignedAssistantError(user);
        }
        currentAssistant.addRecommendation(recommendation);
    }

    public void initFrom(final ClientRegistration event) {
        this.user = event.newClient() ? new UserEntity(event.name()) : new UserEntity(event.clientId(), event.name());
        if (eventHistory == null) eventHistory = new ArrayList<>();
        eventHistory.add(new DomainEvent(event.toRawValue()));
    }

    public UserId id() {
        return user.getId();
    }

    public <T extends ClientEvent> void apply(final T event) {
        eventHistory.add(new DomainEvent(event.toRawValue()));
    }
}
