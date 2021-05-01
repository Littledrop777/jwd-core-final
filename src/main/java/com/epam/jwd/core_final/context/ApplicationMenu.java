package com.epam.jwd.core_final.context;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        System.out.println("Oops. Something wrong");
    }

    default int handleUserInput() {
        System.out.println("Something wrong");
        return 0;
    }
}
