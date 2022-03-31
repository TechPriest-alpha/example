package my.playground.orm.secondtry.entities.lazyness;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class MappedForLazyTest {
    @Test
    @DisplayName("Email in Message will be not null")
    void emailInMessageWillBeNotNull() {
        Long emailId; UUID messageId; MappedEmailLazy email; MappedMessageLazy message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailLazy("Inverse email");
            message = new MappedMessageLazy("Inverse message");
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
            email = session.get(MappedEmailLazy.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessageLazy.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: refresh email")
    void emailInMessageWillBeNotNull_refresh() {
        Long emailId; UUID messageId; MappedEmailLazy email; MappedMessageLazy message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailLazy("Inverse email");
            message = new MappedMessageLazy("Inverse message");
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
            email = session.get(MappedEmailLazy.class, emailId);
            log.info("Email: {}", email);
            message = session.get(MappedMessageLazy.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null: cascading")
    void emailInMessageWillBeNotNull_cascading() throws InterruptedException {
        Long emailId; UUID messageId; MappedEmailLazy email; MappedMessageLazy message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new MappedEmailLazy("Inverse email");
            message = new MappedMessageLazy("Inverse message");
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
            message = session.get(MappedMessageLazy.class, messageId);
            log.info("1 Message selected but not printed");
            log.info("1 Message: {}, email: {}", message, email);
            email = session.get(MappedEmailLazy.class, emailId);
            log.info("1 Email selected but not printed");
            log.info("1 Email: {}, message: {}", email, message);
        }

        try (final var session = SessionUtil.getSession()) {
            log.info("2 Before email select");
            email = session.get(MappedEmailLazy.class, emailId);
            log.info("2 Email selected but not printed");
            log.info("2 Email: {}, message: {}", email, message);
            message = session.get(MappedMessageLazy.class, messageId);
            log.info("2 Message selected but not printed");
            log.info("2 Message: {}, email: {}", message, email);

        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }
}