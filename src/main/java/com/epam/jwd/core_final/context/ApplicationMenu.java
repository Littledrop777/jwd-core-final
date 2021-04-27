package com.epam.jwd.core_final.context;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default Object printAvailableOptions() { //todo print to console only?
        return null;
    }

    default Object handleUserInput(Object o) {
        return null;
    }
}
