package my.playground.orm;

import lombok.extern.slf4j.Slf4j;
import my.playground.orm.firsttry.OrmOperations;
import my.playground.orm.firsttry.TryEvents;
import my.playground.orm.firsttry.events.ClientRegistration;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class Application {
    public static final AtomicBoolean run = new AtomicBoolean(true);

    public static void main(final String[] args) throws Exception {
        try (final var weld = new Weld().initialize()) {
            runScript(weld);
            while (run.get()) {
                synchronized (run) {
                    run.wait();
                }
            }
        }
    }

    private static void runScript(final WeldContainer weld) throws ExecutionException, InterruptedException {
        weld.select(Hibernator.class).get().getX();
        final var basicOperation = weld.select(OrmOperations.class).get();
        log.info("Found helper: {}", basicOperation.readHelper(1L));

        final var events = weld.event().select().fireAsync(new ClientRegistration("New client Async")).toCompletableFuture().get();
        weld.select(TryEvents.class).get().send(new ClientRegistration("New client Sync"));
        log.info("Events: {}", events);
//        final UserEntity clientUser = new UserEntity("First Client");
//        basicOperation.write(clientUser);
//        final UserEntity assistantUser = new UserEntity("First Assistant");
//        basicOperation.write(assistantUser);
//        final AssistantEntity helper = new AssistantEntity(1L, assistantUser, "super helper", new ArrayList<>());
//        basicOperation.write(helper);
        log.info("All users: {}", basicOperation.readAllUsers());
        log.info("All clients: {}", basicOperation.readAllClients());
        log.info("All helpers: {}", basicOperation.readAllHelpers());
//        final var client = new Client(clientUser);
//        client.assignAssistant(helper);
//        client.rateAssistant(new Recommendation(client.id(), assistantUser.getId(), "Nice!", Grade.HIGH));
//        log.info("Now helper is: {}", helper);
    }
}
