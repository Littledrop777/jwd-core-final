package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.Planet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public enum NassaMenu implements ApplicationMenu {
    INSTANCE;

    public static final Logger LOGGER = LoggerFactory.getLogger(NassaMenu.class);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println("Pleas, choose one option");
        System.out.println("1.Get all Planets\n" +
                "2.Mission menu\n" +
                "3.Spaceship menu\n" +
                "4.Crew member menu\n" +
                "0.Exit");

        int input = handleUserInput();
        switch (input) {
            case 1:
                LOGGER.info("Handle user input {}. Get all planets.", input);
                getApplicationContext().retrieveBaseEntityList(Planet.class).forEach(System.out::println);
                System.out.println();
                printAvailableOptions();
                break;
            case 2:
                LOGGER.info("Handle user input {}. Mission menu.", input);
                FlightMissionMenu.INSTANCE.printAvailableOptions();
                break;
            case 3:
                LOGGER.info("Handle user input {}. Spaceship menu.", input);
                SpaceshipMenu.INSTANCE.printAvailableOptions();
                break;
            case 4:
                LOGGER.info("Handle user input {}. Crew member menu.", input);
                CrewMemberMenu.INSTANCE.printAvailableOptions();
                break;
            case 0:
                LOGGER.info("Exit");
                break;
        }
    }

    @Override
    public int handleUserInput() {
        int input;
        while (true) {
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Incorrect input. Please, enter a number of option");
            }
            input = scanner.nextInt();
            int maxNumberOfOption = 4;
            if (input <= maxNumberOfOption) {
                break;
            }
            System.out.println("Incorrect input. Please, try again");
        }
        return input;
    }
}
