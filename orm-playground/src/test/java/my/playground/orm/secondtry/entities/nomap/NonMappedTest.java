package my.playground.orm.secondtry.entities.nomap;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.secondtry.services.SessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class NonMappedTest {
    @Test
    @DisplayName("Email in Message will be null")
    void emailInMessageWillBeNull() {
        Long emailId; UUID messageId; NonMappedEmail email; NonMappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new NonMappedEmail("Broken");
            message = new NonMappedMessage("Broken");
            email.setMessage(message);
            // message.setEmail(email);
            session.persist(email);
            session.persist(message);
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
        }
        assertNotNull(email.getMessage());
        assertNull(message.getEmail());

        try (final var session = SessionUtil.getSession()) {
            email = session.get(NonMappedEmail.class, emailId);
            log.info("Email: {}", email);
            message = session.get(NonMappedMessage.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNull(message.getEmail());
    }

    @Test
    @DisplayName("Email in Message will be not null")
    void emailInMessageWillBeNotNull() {
        Long emailId; UUID messageId; NonMappedEmail email; NonMappedMessage message;
        try (final var session = SessionUtil.getSession()) {
            final var tx = session.beginTransaction();
            email = new NonMappedEmail("Broken");
            message = new NonMappedMessage("Broken");
            email.setMessage(message);
            message.setEmail(email);
            session.persist(email);
            session.persist(message);
            tx.commit();
            emailId = email.getId();
            messageId = message.getId();
        }
        assertNotNull(email.getMessage());
        assertNotNull(message.getEmail());

        try (final var session = SessionUtil.getSession()) {
            email = session.get(NonMappedEmail.class, emailId);
            log.info("Email: {}", email);
            message = session.get(NonMappedMessage.class, messageId);
            log.info("Message: {}", message);
        }
        assertNotNull(email.getMessage());

        assertNotNull(message.getEmail());
    }

}