package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.factory.impl.FlightMissionFactory;
import com.epam.jwd.core_final.reader.PatternsForValidation;

import java.util.Scanner;

public class NassaMenu implements ApplicationMenu {
    private static NassaMenu instance;
    private final Scanner scanner = new Scanner(System.in);
    private final FlightMissionFactory factory = FlightMissionFactory.getInstance();


    private NassaMenu() {
    }

    public static NassaMenu getInstance() {
        if (instance == null) {
            instance = new NassaMenu();
        }
        return instance;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }


    public void print() {
        ApplicationContext context = getApplicationContext();

        boolean enter = true;

        while (enter) {
            System.out.println(Option.START_OPTIONS);
            int choice = handleUserInput();
            switch (choice) {
                case 1:
                    context.retrieveBaseEntityList(Planet.class).forEach(System.out::println);
                    System.out.println();
                    break;
                case 2:
                    System.out.println(Option.MISSION_OPTIONS);
                    choice = handleUserInput();
                    switch (choice) {
                        case 1:
                            String[] data = takeDataForMission();
                            printMission(data);
                            break;
                        case 2:
                            FlightMission flightMission = factory.generateMission("First mission");
                            context.addFlightMission(flightMission);
                            System.out.println(flightMission);
                            break;
                    }
                    break;
                case 3:
                    System.out.println(Option.SPACESHIP_OPTIONS);
                    choice = handleUserInput();
                    switch (choice) {
                        case 1:
                            System.out.println("Sorry, this is hasn't work yet");
                            break;
                        case 2:
                            context.retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
                            break;
                    }
                    break;
                case 4:
                    System.out.println(Option.CREW_MEMBER_OPTIONS);
                    choice = handleUserInput();
                    switch (choice) {
                        case 1:
                            System.out.println("Sorry, this is hasn't work yet");
                            break;
                        case 2:
                            context.retrieveBaseEntityList(CrewMember.class).forEach(System.out::println);
                            break;
                    }
                    break;
                default:
                    enter = false;
            }
        }
    }

    private String[] takeDataForMission() {
        ApplicationContext context = getApplicationContext();
        System.out.println("Enter mission name");
        String name = scanner.nextLine();
        context.retrieveBaseEntityList(Planet.class).forEach(System.out::println);
        System.out.println("Choose planet from");
        String planetFrom = scanner.nextLine();
        System.out.println("Choose planet to");
        String planetTo = scanner.nextLine();
        System.out.println("Choose spaceship");
        context.retrieveBaseEntityList(Spaceship.class).forEach(System.out::println);
        String spaceship = scanner.nextLine();
        System.out.println("Enter start date in format yyyy-MM-dd HH:mm:ss");
        String startDate = inputString(PatternsForValidation.DATE_FORMAT);
        System.out.println("Enter end date in format yyyy-MM-dd HH:mm:ss");
        String endDate = inputString(PatternsForValidation.DATE_FORMAT);
        return new String[]{name, planetFrom, planetTo, spaceship, startDate, endDate};
    }

    public void printMission(String[] missionData){
        FlightMission flightMission = factory.create((Object) missionData);
        System.out.println(flightMission);
    }

    public String inputString(String regex) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        while (true) {
            if(text.matches(regex)){
                break;
            }
            scanner.next();
            System.out.println("Incorrect date. Please, try again");
        }
        return scanner.nextLine();
    }
}
