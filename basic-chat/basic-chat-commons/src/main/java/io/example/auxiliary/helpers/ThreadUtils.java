package io.example.auxiliary.helpers;

import io.example.auxiliary.errors.GeneralInternalError;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class ThreadUtils {

    public static void await(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }

    public static void awaitRandom(final int minDelay, final int maxDelay) {
        await(ThreadLocalRandom.current().nextInt(minDelay, maxDelay));
    }
}
