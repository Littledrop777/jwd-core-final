package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.reader.PatternsForValidation;
import com.epam.jwd.core_final.service.MissionService;
import com.epam.jwd.core_final.service.impl.FlightMissionServiceImpl;
import com.epam.jwd.core_final.util.PropertyReaderUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

public enum FlightMissionMenu implements ApplicationMenu {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMissionMenu.class);
    private final MissionService missionService = FlightMissionServiceImpl.INSTANCE;
    private final PropertyReaderUtil properties = PropertyReaderUtil.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int maxNumberOfOptions;

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println("Flight mission menu");
        System.out.println("1.Create mission\n" +
                "2.Generate mission\n" +
                "3.Get all missions\n" +
                "4.Return\n" +
                "0.Exit");
        maxNumberOfOptions = 4;
        int input = handleUserInput();
        String name;
        switch (input) {
            case 1:
                LOGGER.info("Handle user input {}. Create mission.", input);
                System.out.println("Enter mission name");
                name = scanner.next();
                Object[] missionData = takeDataForMission(name);
                FlightMission flightMission = FlightMissionFactory.getInstance().create(missionData);
                flightMission = FlightMissionServiceImpl.INSTANCE.createMission(flightMission);
                LOGGER.info("Mission was created {}", flightMission);
                System.out.println(flightMission);
                optionForSave(flightMission);
                optionForUpdate(flightMission);
                printAvailableOptions();
                break;
            case 2:
                LOGGER.info("Handle user input {}. Generate mission", input);
                System.out.println("Enter mission name");
                name = scanner.next();
                FlightMission randomMission = missionService.generateMission(name);
                LOGGER.info("Mission was created {}", randomMission);
                System.out.println(randomMission);
                optionForSave(randomMission);
                optionForUpdate(randomMission);
                printAvailableOptions();
                break;
            case 3:
                LOGGER.info("Handle user input {}. Get all missions", input);
                Collection<FlightMission> flightMissions = getApplicationContext().retrieveBaseEntityList(FlightMission.class);
                if (flightMissions.isEmpty()) {
                    LOGGER.info("Storage of missions is empty");
                    System.out.println("The flight mission storage is empty");
                } else {
                    flightMissions.forEach(System.out::println);
                    LOGGER.info("Get storage of missions {}", flightMissions);
                }
                printAvailableOptions();
                break;
            case 4:
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

    private void optionForSave(FlightMission mission) {
        System.out.println("Do you want save mission in file? (Y/N)");
        String answer = scanner.next();
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println("Incorrect input. Please, enter \"Y\" or \"N\"");
            answer = scanner.next();
        }
        switch (answer) {
            case "Y":
                saveFlightMission(mission);
                LOGGER.info("Mission was successfully saved {}", mission);
                break;
            case "N":
                break;
        }
    }

    private void saveFlightMission(FlightMission mission) {
        String path = "src/main/resources";
        String filePath = path + File.separator + properties.getOutputRootDir() + File.separator + properties.getMissionsFileName();
        try (PrintWriter printWriter = new PrintWriter(
                new FileWriter(filePath, true))) {
                printWriter.println(objectMapper.writeValueAsString(mission));
            System.out.println("Mission successfully saved");
        } catch (IOException e) {
           LOGGER.error(e.getMessage());
        }
    }

    private void optionForUpdate(FlightMission mission) {
        System.out.println("Do you want update mission? (Y/N)");
        String answer = scanner.next();
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println("Incorrect input. Please, enter \"Y\" or \"N\"");
            answer = scanner.next();
        }
        switch (answer) {
            case "Y":
                updateFlightMission(mission);
                LOGGER.info("Mission was successfully update {}", mission);
                break;
            case "N":
                break;
        }
    }

    private Object[] takeDataForMission(String name) {
        getApplicationContext().retrieveBaseEntityList(Planet.class).forEach(System.out::println);
        System.out.println("Choose planet from");
        Planet planetFrom = inputPlanet();
        System.out.println("Choose planet to");
        Planet planetTo = inputPlanet();
        while (planetFrom.equals(planetTo)) {
            System.out.println("Please, enter another planet");
            planetTo = inputPlanet();
        }
        getApplicationContext().retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
        System.out.println("Choose spaceship");
        Spaceship spaceship = inputSpaceship();
        System.out.println("Enter start date in format yyyy-MM-dd");
        String startDate = inputDate(PatternsForValidation.DATE_FORMAT);
        System.out.println("Enter end date in format yyyy-MM-dd");
        String endDate = inputDate(PatternsForValidation.DATE_FORMAT);
        return new Object[]{name, planetFrom, planetTo, spaceship, startDate, endDate};
    }

    private void updateFlightMission(FlightMission mission) {
        Object[] data = takeDataForMission(mission.getName());
        FlightMission newMission = FlightMissionFactory.getInstance().create(data);
        long id = mission.getId();
        newMission = FlightMissionFactory.getInstance().assignId(newMission, id);
        missionService.updateMission(newMission);
        System.out.println("Update successfully\n");
    }

    public String inputDate(String regex) {
        String text;
        while (true) {
            text = scanner.nextLine();
            if (text.matches(regex)) {
                break;
            }
            System.out.println("Incorrect date. Please, try again");
        }
        return text;
    }

    public Planet inputPlanet() {
        String name;
        Planet planet;
        while (true) {
            name = scanner.nextLine();
            String finalText = name;
            if (getApplicationContext().retrieveBaseEntityList(Planet.class).stream()
                    .anyMatch(existPlanet -> existPlanet.getName().equals(finalText))) {
                planet = getApplicationContext().retrieveBaseEntityList(Planet.class).stream()
                        .filter(existPlanet -> existPlanet.getName().equals(finalText)).findFirst().get();
                break;
            }
            System.out.println("Planet with this name is not exist");
        }
        return planet;
    }

    public Spaceship inputSpaceship() {
        String name;
        Spaceship spaceship;
        while (true) {
            name = scanner.nextLine();
            String finalText = name;
            if (getApplicationContext().retrieveBaseEntityList(Spaceship.class).stream()
                    .anyMatch(existPlanet -> existPlanet.getName().equals(finalText))) {
                spaceship = getApplicationContext().retrieveBaseEntityList(Spaceship.class).stream()
                        .filter(existSpaceship -> existSpaceship.getName().equals(finalText)).findFirst().get();
                break;
            }
            System.out.println("Spaceship with this name is not exist");
        }
        return spaceship;
    }
}
