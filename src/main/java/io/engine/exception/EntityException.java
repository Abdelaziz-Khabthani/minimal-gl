package io.engine.exception;

public class EntityException extends RuntimeException {
    public EntityException(final String message) {
        super(message);
    }

    public EntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
