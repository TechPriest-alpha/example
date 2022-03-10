package my.playground.orm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import my.playground.orm.entities.Client;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.AssistantEntity;
import my.playground.orm.entities.sub.UserId;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
@Slf4j
@ApplicationScoped
public class BasicOperation {
    private final EntityManager em;

    @Inject
    public BasicOperation(final EntityManager em) {
        this.em = em;
    }

    public UserEntity readUser(final UserId id) {
        return em.find(UserEntity.class, id);
    }

    public AssistantEntity readHelper(final Long assistanceId) {
        return em.find(AssistantEntity.class, assistanceId);
    }

    @Transactional
    public void write(final Object entity) {
        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if (!em.contains(entity)) em.merge(entity);
            em.persist(entity);
            em.flush();
            transaction.commit();
        } catch (final Exception ex) {
            log.error("Error in write", ex);
            transaction.rollback();
        }
    }

    public Client findClient(final UserId clientId) {
        return em.find(Client.class, clientId);
    }

    public List<UserEntity> readAllUsers() {
        final CriteriaQuery<UserEntity> criteria = em.getCriteriaBuilder().createQuery(UserEntity.class).distinct(true);
        criteria.select(criteria.from(UserEntity.class));

        return em.createQuery(criteria).getResultList();
    }

    public List<Client> readAllClients() {
        final var criteria = em.getCriteriaBuilder().createQuery(Client.class).distinct(true);
        criteria.select(criteria.from(Client.class));
        return em.createQuery(criteria).getResultList();
    }

    public List<AssistantEntity> readAllHelpers() {
        final var criteria = em.getCriteriaBuilder().createQuery(AssistantEntity.class).distinct(true);
        criteria.select(criteria.from(AssistantEntity.class));
        return em.createQuery(criteria).getResultList();
    }
}
