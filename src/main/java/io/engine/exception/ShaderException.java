package io.engine.exception;

public class ShaderException extends RuntimeException{
    public ShaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShaderException(String message) {
        super(message);
    }
}
