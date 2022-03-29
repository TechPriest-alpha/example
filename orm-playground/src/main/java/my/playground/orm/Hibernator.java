package my.playground.orm;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        final var metadataSources = new MetadataSources(standardRegistry);
        findAllClassesUsingClassLoader("my.playground.orm.firsttry.entities").forEach(metadataSources::addAnnotatedClass);
        findAllClassesUsingClassLoader("my.playground.orm.secondtry.entities").forEach(metadataSources::addAnnotatedClass);
        final var metadata = metadataSources.buildMetadata();
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
            log.info("Hi: {}, em: {}", s.isOpen(), em.isOpen());
        }
    }

    @Produces
    @Named
    public EntityManager entityManager() {
        return em;
    }

    public Set<Class<?>> findAllClassesUsingClassLoader(final String packageName) {
        try (final InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
             final BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines()
                .flatMap(line -> line.endsWith(".class") ? Stream.of(getClass(line, packageName)) : findAllClassesUsingClassLoader(packageName + "." + line).stream())
                .filter(Objects::nonNull)
                .filter(cls -> cls.isAnnotationPresent(Entity.class))
                .collect(Collectors.toSet());
        } catch (final Exception ex) {
            throw new RuntimeException("Error in class scanning", ex);
        }
    }

    private Class<?> getClass(final String className, final String packageName) {
        try {
            return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
        } catch (final ClassNotFoundException e) {
            log.error("Not found", e);
        }
        return null;
    }
}