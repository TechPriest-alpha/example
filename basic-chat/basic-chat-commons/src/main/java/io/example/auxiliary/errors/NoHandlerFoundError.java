package io.example.auxiliary.errors;

public class NoHandlerFoundError extends RuntimeException {
    public NoHandlerFoundError() {
    }

    public NoHandlerFoundError(final String message) {
        super(message);
    }

    public NoHandlerFoundError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoHandlerFoundError(final Throwable cause) {
        super(cause);
    }

    public NoHandlerFoundError(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
