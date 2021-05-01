package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.reader.Separator;
import com.epam.jwd.core_final.service.SpaceshipService;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public enum SpaceshipMenu implements ApplicationMenu {
    INSTANCE;

    public static final Logger LOGGER = LoggerFactory.getLogger(SpaceshipMenu.class);
    private final SpaceshipService spaceshipService = SpaceshipServiceImpl.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    private int maxNumberOfOptions;

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println("Spaceship menu:");
        System.out.println("1.Get spaceship\n" +
                "2.Get all spaceships\n" +
                "3.Return\n" +
                "0.Exit");
        maxNumberOfOptions = 3;
        int input = handleUserInput();
        switch (input) {
            case 1:
                LOGGER.info("Handle user input {}. Get spaceship.", input);
                printSpaceship();
                printAvailableOptions();
                break;
            case 2:
                LOGGER.info("Handle user input {}. Get all spaceships.", input);
                spaceshipService.findAllSpaceships().forEach(System.out::println);
                printAvailableOptions();
                break;
            case 3:
                LOGGER.info("Return");
                NassaMenu.INSTANCE.printAvailableOptions();
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
            if (input <= maxNumberOfOptions) {
                break;
            }
            System.out.println("Incorrect input. Please, try again.");
        }
        return input;
    }

    private void printSpaceship() {
        System.out.println("1.Get spaceship by criteria\n" +
                "2.Get all spaceships by criteria\n" +
                "3.Get random spaceship\n");
        maxNumberOfOptions = 3;
        int input = handleUserInput();
        SpaceshipCriteria criteria;
        switch (input) {
            case 1:
                System.out.println("Choose one or more number of criteria options (separate with comma!)\n");
                criteria = enterCriteria();
                Spaceship spaceship = spaceshipService.findSpaceshipByCriteria(criteria).orElse(null);
                if (spaceship == null) {
                    System.out.println("No such spaceship");
                    printSpaceship();
                    break;
                }
                System.out.println(spaceship);
                optionForUpdate(spaceship);
                break;
            case 2:
                criteria = enterCriteria();
                spaceshipService.findAllSpaceshipsByCriteria(criteria)
                        .forEach(System.out::println);
                break;
            case 3:
                Spaceship randomSpaceship = spaceshipService.getRandomSpaceship();
                System.out.println(randomSpaceship);
                optionForUpdate(randomSpaceship);
                break;
        }
    }

    private SpaceshipCriteria enterCriteria() {
        System.out.println("1.Id\n" +
                "2.Name\n" +
                "3.Flight distance\n");

        String options = enterOptionsOfCriteriaWithCheck();
        String[] numbersOfOptions = options.replaceAll("\\s", "").split(Separator.SPLIT_BY_COMA);
        return takeCriteria(numbersOfOptions);
    }

    private SpaceshipCriteria takeCriteria(String[] options) {
        long id = 0L;
        String name = null;
        long distance = 0L;
        for (String numberOfOption : options) {
            int number = Integer.parseInt(numberOfOption);
            switch (number) {
                case 1:
                    System.out.println("Enter id");
                    id = enterLongNumber();
                    break;
                case 2:
                    System.out.println("Enter name");
                    name = scanner.next();
                    break;
                case 3:
                    System.out.println("Enter distance");
                    distance = enterLongNumber();
                    break;
            }
        }
        return SpaceshipCriteria.create()
                .whereId(id)
                .whereName(name)
                .whereFlightDistance(distance)
                .build();
    }

    private String enterOptionsOfCriteriaWithCheck() {
        String criteria = scanner.nextLine();
        String multiInput = "[1-3]((,[1-3])+)?";
        while (!criteria.matches(multiInput)) {
            System.out.println("Incorrect enter. Please try again");
            criteria = scanner.nextLine();
        }
        return criteria;
    }

    private long enterLongNumber() {
        long id;
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Incorrect input. Please, enter a number");
        }
        id = scanner.nextLong();
        return id;
    }

    private void optionForUpdate(Spaceship spaceship) {
        System.out.println("Do you want update spaceship? (Y/N)");
        String answer = scanner.next();
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println("Incorrect input. Please, enter \"Y\" or \"N\"");
            answer = scanner.next();
        }
        switch (answer) {
            case "Y":
                System.out.println("Choose criteria for update\n");
                updateSpaceship(spaceship);
                break;
            case "N":
                break;
        }
    }

    private void updateSpaceship(Spaceship spaceship) {
        System.out.println("Enter distance");
        long distance = enterLongNumber();
        Spaceship newSpaceship = SpaceshipFactory.getInstance().create(spaceship.getName(), distance, spaceship.getCrew());
        spaceshipService.updateSpaceshipDetails(newSpaceship);
        System.out.println("Update successfully\n");
        System.out.println("-------------------------");
    }
}
