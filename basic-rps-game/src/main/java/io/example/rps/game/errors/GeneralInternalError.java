package io.example.rps.game.errors;

public class GeneralInternalError extends RuntimeException {
    public GeneralInternalError() {
    }

    public GeneralInternalError(final String message) {
        super(message);
    }

    public GeneralInternalError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GeneralInternalError(final Throwable cause) {
        super(cause);
    }

    public GeneralInternalError(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
