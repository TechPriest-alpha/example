package my.playground.orm.firsttry.entities;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import my.playground.orm.firsttry.domain.errors.AssistantAlreadyAssignedError;
import my.playground.orm.firsttry.domain.errors.AssistantSameAsClientError;
import my.playground.orm.firsttry.domain.errors.NoAssignedAssistantError;
import my.playground.orm.firsttry.entities.sub.AssistantEntity;
import my.playground.orm.firsttry.entities.sub.DomainEvent;
import my.playground.orm.firsttry.events.ClientEvent;
import my.playground.orm.firsttry.events.ClientRegistration;
import my.playground.orm.firsttry.events.Recommendation;
import my.playground.orm.firsttry.events.entity.CreateUser;
import my.playground.orm.firsttry.events.entity.FromClient;
import my.playground.orm.firsttry.events.entity.SaveClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Worst thing is that we pollute our class with numerous JPA annotations.
 * In case of complex logic we'll end up with class cluttered with event sends, JPA details and custom boilerplate for all of it.
 */
@Entity
@EqualsAndHashCode(of = {"clientId"})
@ToString
public class Client {
    @Transient
    private Event<FromClient> eventPublisher;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    private Long clientId;

    @OneToOne
    private UserEntity user;

    @OneToOne
    private AssistantEntity currentAssistant;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DomainEvent> eventHistory;

    @Version
    private Long version;

    @Inject
    public Client(final Event<FromClient> publishEvent) {
        this.eventPublisher = publishEvent;
    }

    public Client() {
    }

    public Client(final Long clientId) {
        this.clientId = clientId;
    }

    public void assignAssistant(final AssistantEntity assistant) {
        if (Objects.equals(assistant.getId(), user.getId())) {
            throw new AssistantSameAsClientError(user);
        }
        if (this.currentAssistant != null) {
            throw new AssistantAlreadyAssignedError(user, currentAssistant, assistant);
        }
        this.currentAssistant = assistant;
        eventPublisher.fire(new SaveClient(this));
    }

    public void rateAssistant(final Recommendation recommendation) {
        if (currentAssistant == null) {
            throw new NoAssignedAssistantError(user);
        }
        currentAssistant.addRecommendation(recommendation);
        eventPublisher.fire(new SaveClient(this));
    }

    public void initFrom(final UserEntity user, final ClientRegistration event) {
        this.user = user;
        if (eventHistory == null) eventHistory = new ArrayList<>();
        eventHistory.add(new DomainEvent(event.toRawValue()));
        eventPublisher.fire(new CreateUser(user));
        eventPublisher.fire(new SaveClient(this)); //actually that is wrong, domain object should not fire such events, saving should be handled in the background 'automagically'
    }

    public <T extends ClientEvent> void apply(final T event) {
        eventHistory.add(new DomainEvent(event.toRawValue()));
        if (event instanceof Recommendation rec) rateAssistant(rec);
        if (event instanceof AssistantEntity assistant) assignAssistant(assistant);
    }

}
