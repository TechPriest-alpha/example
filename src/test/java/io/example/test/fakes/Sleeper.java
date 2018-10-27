package io.example.test.fakes;

/**
 * @author Viktor
 * @since 2018-10-27
 */
public interface Sleeper {

    default void delayMax() {
        try {
//            ThreadLocalRandom.current().nextInt(100, 600)
            Thread.sleep(200L);
        } catch (final Exception ignored) {
        }
    }

    default void delayAvg() {
        try {
//            ThreadLocalRandom.current().nextInt(100, 600)
            Thread.sleep(100L);
        } catch (final Exception ignored) {}
    }

    default void delayMin() {
        try {
//            ThreadLocalRandom.current().nextInt(100, 600)
            Thread.sleep(50L);
        } catch (final Exception ignored) {
        }
    }
}
