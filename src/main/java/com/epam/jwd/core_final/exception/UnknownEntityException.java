package com.epam.jwd.core_final.exception;

import java.util.Arrays;
import java.util.Objects;

public class UnknownEntityException extends RuntimeException {

    private final String entityName;
    private final Object[] args;

    public UnknownEntityException(String entityName) {
        super();
        this.entityName = entityName;
        this.args = null;
    }

    public UnknownEntityException(String entityName, Object[] args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        // you should use entityName, args (if necessary)
        String massage = "Unknown entity ";
        if (Objects.nonNull(entityName)) {
            massage = massage + entityName;
        }
        if (Objects.nonNull(args)) {
            massage = massage + ' ' + Arrays.asList(args);
        }
        return massage;
    }
}
