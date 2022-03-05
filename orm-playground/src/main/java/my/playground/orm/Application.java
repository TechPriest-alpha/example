package my.playground.orm;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.entities.UserEntity;
import my.playground.orm.entities.sub.HelperEntity;
import org.jboss.weld.environment.se.Weld;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Application {
    public static final AtomicBoolean run = new AtomicBoolean(true);

    public static void main(final String[] args) throws InterruptedException {
        try (final var weld = new Weld().initialize()) {
            weld.select(Hibernator.class).get().getX();
            final var basicOperation = weld.select(BasicOperation.class).get();
            final UserEntity user = new UserEntity(1L, "First");
            basicOperation.write(user);
            final HelperEntity helper = new HelperEntity(1L, user, "super helper");
            basicOperation.write(helper);
            log.info("Found user: {}", basicOperation.readUser(1L));
            log.info("Found helper: {}", basicOperation.readHelper(1L));
            while (run.get()) {
                synchronized (run) {
                    run.wait();
                }
            }
        }
    }
}
