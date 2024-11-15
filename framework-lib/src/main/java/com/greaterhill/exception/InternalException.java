package com.greaterhill.exception;

import java.io.Serial;

public class InternalException extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    public InternalException(String message) {
        super(message);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }
}