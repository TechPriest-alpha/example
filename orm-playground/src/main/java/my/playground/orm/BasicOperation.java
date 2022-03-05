package my.playground.orm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.HelperEntity;

@SuppressWarnings("ClassCanBeRecord")
@ApplicationScoped
public class BasicOperation {
    private final EntityManager em;

    @Inject
    public BasicOperation(final EntityManager em) {
        this.em = em;
    }

    public UserEntity readUser(final Long id) {
        return em.find(UserEntity.class, id);
    }

    public HelperEntity readHelper(final long helperId) {
        return em.find(HelperEntity.class, helperId);
    }

    @Transactional
    public void write(final Object entity) {
        em.persist(entity);
    }
}
