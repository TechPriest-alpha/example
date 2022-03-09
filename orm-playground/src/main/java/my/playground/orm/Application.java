package my.playground.orm;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.domain.Client;
import my.playground.orm.domain.Grade;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.AssistantEntity;
import my.playground.orm.events.Recommendation;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Application {
    public static final AtomicBoolean run = new AtomicBoolean(true);

    public static void main(final String[] args) throws InterruptedException {
        try (final var weld = new Weld().initialize()) {
            runScript(weld);
            while (run.get()) {
                synchronized (run) {
                    run.wait();
                }
            }
        }
    }

    private static void runScript(final WeldContainer weld) {
        weld.select(Hibernator.class).get().getX();

        final var basicOperation = weld.select(BasicOperation.class).get();
        log.info("Found helper: {}", basicOperation.readHelper(1L));

        final UserEntity clientUser = new UserEntity("First Client");
        basicOperation.write(clientUser);
        final UserEntity assistantUser = new UserEntity("First Assistant");
        basicOperation.write(assistantUser);
        final AssistantEntity helper = new AssistantEntity(1L, assistantUser, "super helper", new ArrayList<>());
        basicOperation.write(helper);
        log.info("Found user: {}", basicOperation.readUser(clientUser.getId()));
        log.info("Found helper: {}", basicOperation.readHelper(1L));
        final var client = new Client(clientUser);
        client.assignAssistant(helper);
        client.rateAssistant(new Recommendation(client.id(), assistantUser.getId(), "Nice!", Grade.HIGH));
        log.info("Now helper is: {}", helper);
    }
}
