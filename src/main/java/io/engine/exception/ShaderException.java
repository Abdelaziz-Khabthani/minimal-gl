package io.engine.exception;

public class ShaderException extends RuntimeException{
    public ShaderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ShaderException(final String message) {
        super(message);
    }
}
