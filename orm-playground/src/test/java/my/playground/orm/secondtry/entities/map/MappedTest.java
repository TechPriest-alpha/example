package my.playground.orm.secondtry.entities.map;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class MappedTest {
    @Test
    @DisplayName("Email in Message will be not null")
    void emailInMessageWillBeNotNull() {
        Long emailId; Long messageId; MappedEmail email; MappedMessage message;
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
        Long emailId; Long messageId; MappedEmail email; MappedMessage message;
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
    void emailInMessageWillBeNotNull_cascading() {
        Long emailId; Long messageId; MappedEmail email; MappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmail("Inverse email");
            message = new MappedMessage("Inverse message");
//            email.setMessage(message);
            message.setEmail(email);
//            session.persist(email);
            session.persist(message);
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
            log.info("Before refresh: {}", message);
            session.refresh(message);
            log.info("After refresh: {}", message);
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
}