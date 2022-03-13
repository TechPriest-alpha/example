package my.playground.orm.firsttry.domain.errors;

public class BusinessException extends RuntimeException {
    public BusinessException(final String message) {
        super(message, null, true, false);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
