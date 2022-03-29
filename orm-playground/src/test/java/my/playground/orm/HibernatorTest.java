package my.playground.orm;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.entities.SimpleMessage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HibernatorTest {

    private static SessionFactory factory;

    @Test
    @Disabled
    void errorInResourcesCaught() {
        class Stub implements Closeable {
            private static boolean closed = false;

            @Override
            public void close() throws IOException {
                closed = true;
                log.info("Stub closed");
            }
        }
        boolean errorBranch;
        try (var stub = new Stub(); var input = new BufferedReader(null)) {
            log.info("OK: {}", Stub.closed);
            errorBranch = false;
        } catch (final Exception ex) {
            log.info("Error", ex);
            errorBranch = true;
        }
        assertTrue(Stub.closed);
        assertTrue(errorBranch);
    }

    @BeforeAll
    static void beforeAll() {
        final var registry = new StandardServiceRegistryBuilder().configure().build();
        factory = new MetadataSources(registry)
//            .addAnnotatedClass(SimpleMessage.class)
            .buildMetadata()
            .buildSessionFactory();
    }

    @Test
    @Order(1)
    void writeSimple() {

        SimpleMessage message = new SimpleMessage("Hello, world");
        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction(); session.persist(message); tx.commit();
        }
    }

    @Test
    @Order(2)
    void testSimple() {
        try (Session session = factory.openSession()) {
            List<SimpleMessage> list = session.createQuery("from SimpleMessage", SimpleMessage.class).list();

            assertEquals(list.size(), 1);
            for (SimpleMessage m : list) {
                System.out.println(m);
            }
        }
    }

}