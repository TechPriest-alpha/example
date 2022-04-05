package my.playground.orm.secondtry.entities.map;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MappedTest {

    @Test
    @DisplayName("Get data with named package-level query")
    void useNamedQuery() {
        final int i1 = 10;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            addEntry("email1","message with data1", session);

            addEntry("email2", "message with data2", session);

            addEntry("email3", "message with data3", session);

            addEntry("email33", "message with data33", session);
            addEntry("email4", "message with data4", session);
            addEntry("email5", "message with data5", session);
            addEntry("email6", "message with data6", session);
            addEntry("email7", "message with data7", session);
            addEntry("email8", "message with data8", session);
            for (int i = 0; i < i1; i++) {
                addEntry("email-X" + i, "dataX" + i, session);
            }
            tx.commit();
        }

        try (final var session = SessionUtil.getSession()) { //weirdness: execs query per each list item
            log.info("Before query");
            session.setJdbcBatchSize(100);
            final var q = session.createNamedQuery("findMappedMessageByContent", MappedMessage.class);
            q.setParameter("input", "message with data");
//            q.setMaxResults(100);
            log.info("Intermediate: {}", q.getResultList());
            assertEquals(9, q.list().size());
            log.info("After query");
        }

        try (final var session = SessionUtil.getSession()) {
            session.setJdbcBatchSize(100);
            final var q = session.createNamedQuery("findMappedMessageByContent", MappedMessage.class);
//            q.setMaxResults(100);
            q.setParameter("input", "message with data3");
            assertEquals(2, q.list().size());
        }

        try (final var session = SessionUtil.getSession()) { //'effective' hibernate keeps fetching records in list 'one-by-one', no hint on 'why' and how this can be changed.
            var tx = session.beginTransaction();
            final var q = session.createQuery("select mm.id, mm.content, mm.email from MappedMessage mm", Object[].class);
//            q.setMaxResults(100);
            q.setComment("!@#");
            log.info("Data: {}", q.getResultList().stream().map(Arrays::toString).collect(Collectors.toList()));
            assertEquals(i1 + 9, q.list().size());
            tx.commit();
        }
    }

    private void addEntry(final String email33, final String message_with_data33, final Session session) {
        MappedMessage message;
        MappedEmail email;
        email = new MappedEmail(email33);
        message = new MappedMessage(message_with_data33);
        message.setEmail(email);
        session.persist(message);
    }

    @Test
    @DisplayName("Email in Message will be not null")
    void emailInMessageWillBeNotNull() {
        Long emailId; UUID messageId; MappedEmail email; MappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmail("Inverse email");
            message = new MappedMessage("Inverse message");
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
            email = session.get(MappedEmail.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessage.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: refresh email")
    void emailInMessageWillBeNotNull_refresh() {
        Long emailId; UUID messageId; MappedEmail email; MappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmail("Inverse email");
            message = new MappedMessage("Inverse message");
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
            email = session.get(MappedEmail.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessage.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: cascading")
    void emailInMessageWillBeNotNull_cascading() throws InterruptedException {
        Long emailId; UUID messageId; MappedEmail email; MappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmail("Inverse email");
            message = new MappedMessage("Inverse message");
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
            message = session.get(MappedMessage.class, messageId);
            log.info("1 Message selected but not printed");
            log.info("1 Message: {}, email: {}", message, email);
            email = session.get(MappedEmail.class, emailId);
            log.info("1 Email selected but not printed");
            log.info("1 Email: {}, message: {}", email, message);
        }

        try (final var session = SessionUtil.getSession()) {
            log.info("2 Before email select");
            email = session.get(MappedEmail.class, emailId);
            log.info("2 Email selected but not printed");
            log.info("2 Email: {}, message: {}", email, message);
            message = session.get(MappedMessage.class, messageId);
            log.info("2 Message selected but not printed");
            log.info("2 Message: {}, email: {}", message, email);

        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }
}