package io.example.auxiliary.helpers;

import io.example.auxiliary.errors.GeneralInternalError;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadUtils {

    public static void await(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception ex) {
            throw new GeneralInternalError(ex);
        }
    }
}
