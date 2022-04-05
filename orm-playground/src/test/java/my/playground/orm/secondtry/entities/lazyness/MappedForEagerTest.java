package my.playground.orm.secondtry.entities.lazyness;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MappedForEagerTest {
    @Test
    @DisplayName("Get data with named package-level query")
    void useNamedQuery() {
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            var email = new MappedEmailEager("email1");
            var message = new MappedMessageEager("message with data1");
            message.setEmail(email);
            session.persist(message);

            email = new MappedEmailEager("email2");
            message = new MappedMessageEager("message with data2");
            message.setEmail(email);
            session.persist(message);

            email = new MappedEmailEager("email3");
            message = new MappedMessageEager("message with data3");
            message.setEmail(email);
            session.persist(message);

            email = new MappedEmailEager("email33");
            message = new MappedMessageEager("message with data33");
            message.setEmail(email);
            session.persist(message);
            tx.commit();
        }

        try (final var session = SessionUtil.getSession()) { //weirdness: execs query per each list item
            log.info("Before query");
            session.setJdbcBatchSize(100);
            final var q = session.createNamedQuery("findMessageByContent", MappedMessageEager.class);
            q.setParameter("input", "message with data");
            q.setMaxResults(100);
            log.info("Intermediate: {}", q.getResultList());
            assertEquals(4, q.list().size());
            log.info("After query");
        }

        try (final var session = SessionUtil.getSession()) {
            session.setJdbcBatchSize(100);
            final var q = session.createNamedQuery("findMessageByContent", MappedMessageEager.class);
            q.setMaxResults(100);
            q.setParameter("input", "message with data3");
            assertEquals(2, q.list().size());
        }

        try (final var session = SessionUtil.getSession()) {
            final var q = session.createQuery("from MappedMessageEager mm JOIN FETCH MappedEmailEager me on mm.email.id=me.id", MappedMessageEager.class);
            q.setMaxResults(100);
            assertEquals(4, q.list().size());
        }
    }

    @Test
    @DisplayName("Email in Message will be not null")
    void emailInMessageWillBeNotNull() {
        Long emailId; UUID messageId; MappedEmailEager email; MappedMessageEager message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailEager("Inverse email");
            message = new MappedMessageEager("Inverse message");
//            email.setMessage(message);
            message.setEmail(email);
            session.persist(email);
            session.persist(message);
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
        }
        assertNull(email.getMessage());
        assertNotNull(message.getEmail());

        try (final var session = SessionUtil.getSession()) {
            email = session.get(MappedEmailEager.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessageEager.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: refresh email")
    void emailInMessageWillBeNotNull_refresh() {
        Long emailId; UUID messageId; MappedEmailEager email; MappedMessageEager message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailEager("Inverse email");
            message = new MappedMessageEager("Inverse message");
//            email.setMessage(message);
            message.setEmail(email);
            session.persist(email);
            session.persist(message);
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
            log.info("Before refresh: {}", email.getMessage());
            session.refresh(email);
            log.info("After refresh: {}", email.getMessage());
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());

        try (final var session = SessionUtil.getSession()) {
            email = session.get(MappedEmailEager.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessageEager.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: cascading")
    void emailInMessageWillBeNotNull_cascading() throws InterruptedException {
        Long emailId; UUID messageId; MappedEmailEager email; MappedMessageEager message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailEager("Inverse email");
            message = new MappedMessageEager("Inverse message");
//            email.setMessage(message);
            message.setEmail(email);
//            session.persist(email);
            session.persist(message); //persist owner of the relationship in order to auto-create dependent entity
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
            log.info("Before refresh: {}", message);
            session.refresh(message);
            log.info("After refresh: {}", message);
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());
        message = null;
        email = null;
        try (final var session = SessionUtil.getSession()) {
            log.info("1 Before message select");
            message = session.get(MappedMessageEager.class, messageId);
            log.info("1 Message selected but not printed");
            log.info("1 Message: {}, email: {}", message, email);
            email = session.get(MappedEmailEager.class, emailId);
            log.info("1 Email selected but not printed");
            log.info("1 Email: {}, message: {}", email, message);
        }

        try (final var session = SessionUtil.getSession()) {
            log.info("2 Before email select");
            email = session.get(MappedEmailEager.class, emailId);
            log.info("2 Email selected but not printed");
            log.info("2 Email: {}, message: {}", email, message);
            message = session.get(MappedMessageEager.class, messageId);
            log.info("2 Message selected but not printed");
            log.info("2 Message: {}, email: {}", message, email);

        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }
}