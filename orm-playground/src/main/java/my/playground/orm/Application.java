package my.playground.orm;

import org.jboss.weld.environment.se.Weld;

import java.util.concurrent.atomic.AtomicBoolean;

public class Application {
    public static final AtomicBoolean run = new AtomicBoolean(true);
    public static void main(final String[] args) throws InterruptedException {
        try (var weld = new Weld().initialize()) {
            weld.select(Hibernator.class).get().getX();
            while (run.get()) {
                synchronized (run) {
                    run.wait();
                }
            }
        }
    }
}
