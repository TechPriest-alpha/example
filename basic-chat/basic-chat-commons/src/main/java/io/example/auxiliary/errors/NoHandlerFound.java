package io.example.auxiliary.errors;

public class NoHandlerFound extends RuntimeException {
    public NoHandlerFound() {
    }

    public NoHandlerFound(final String message) {
        super(message);
    }

    public NoHandlerFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoHandlerFound(final Throwable cause) {
        super(cause);
    }

    public NoHandlerFound(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
