package io.example.test.fakes;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public interface Sleeper {

    default void delay() {
        try {
//            ThreadLocalRandom.current().nextInt(100, 600)
            Thread.sleep(300L);
        } catch (final Exception ignored) {}
    }
}
