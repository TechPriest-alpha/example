package my.playground.orm;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


@Slf4j
@ApplicationScoped
public class Hibernator {
    private final StandardServiceRegistry standardRegistry;
    private final SessionFactory sessionFactory;
    private final EntityManager em;

    @Inject
    public Hibernator(final BeanManager beanManager) {
        this.standardRegistry = new StandardServiceRegistryBuilder()
            .configure("/hibernate.cfg.xml")
            .build();
        final Metadata metadata = new MetadataSources(standardRegistry).buildMetadata();
        this.sessionFactory = metadata.getSessionFactoryBuilder().applyBeanManager(beanManager).build();
        this.em = sessionFactory.createEntityManager();
    }

    @PreDestroy
    public void stop() {
        em.close();
        sessionFactory.close();
        standardRegistry.close();
        log.info("Hibernator stopped");
    }

    public void getX() {
        try (final var s = sessionFactory.openSession()) {
            log.info("Hi: {}", em.isOpen());
        }
    }
}
