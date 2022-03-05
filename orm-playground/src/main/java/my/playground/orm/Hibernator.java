package my.playground.orm;

import jakarta.enterprise.context.ApplicationScoped;
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
    private final Metadata metadata;
    private final SessionFactory sessionFactory;

    public Hibernator() {
        this.standardRegistry = new StandardServiceRegistryBuilder()
            .configure("/hibernate.cfg.xml")
            .build();
        this.metadata = new MetadataSources(standardRegistry).buildMetadata();
        this.sessionFactory = metadata.buildSessionFactory();
    }

    public void getX() {
        try (final var s = sessionFactory.openSession()) {
            log.info("Hi");
        }
    }
}
