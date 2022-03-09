package my.playground.orm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.AssistantEntity;
import my.playground.orm.entities.sub.UserId;

@SuppressWarnings("ClassCanBeRecord")
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
            em.persist(entity);
            em.flush();
            transaction.commit();
        } catch (final Exception ex) {
            transaction.rollback();
        }
    }
}
