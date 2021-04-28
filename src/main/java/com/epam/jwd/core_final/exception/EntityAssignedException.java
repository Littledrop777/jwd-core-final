package com.epam.jwd.core_final.exception;

public class EntityAssignedException extends RuntimeException {
    public EntityAssignedException() {
        super();
    }

    public EntityAssignedException(String message) {
        super(message);
    }

    public EntityAssignedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityAssignedException(Throwable cause) {
        super(cause);
    }
}
