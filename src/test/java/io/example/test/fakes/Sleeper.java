package io.example.test.fakes;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public interface Sleeper {

    default void delay() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 600));
        } catch (final Exception ignored) {}
    }
}
