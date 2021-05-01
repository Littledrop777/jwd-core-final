package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.reader.Separator;
import com.epam.jwd.core_final.service.CrewService;
import com.epam.jwd.core_final.service.impl.CrewMemberServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public enum CrewMemberMenu implements ApplicationMenu {
    INSTANCE;

    public static final Logger LOGGER = LoggerFactory.getLogger(CrewMemberMenu.class);
    private final CrewService crewMemberService = CrewMemberServiceImpl.getInstance();
    private final Scanner scanner = new Scanner(System.in);
    private int maxNumberOfOptions;

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    @Override
    public void printAvailableOptions() {
        System.out.println("Crew member menu:");
        System.out.println("1.Get crew member\n" +
                "2.Get all crew members\n" +
                "3.Return\n" +
                "0.Exit");
        maxNumberOfOptions = 3;
        int input = handleUserInput();
        switch (input) {
            case 1:
                LOGGER.info("Handle user input {}. Get crew member.", input);
                printCrewMember();
                printAvailableOptions();
                break;
            case 2:
                LOGGER.info("Handle user input {}. Get all crew members", input);
                crewMemberService.findAllCrewMembers().forEach(System.out::println);
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


    private void printCrewMember() {
        System.out.println("1.Get crew member by criteria\n" +
                "2.Get all crew members by criteria\n" +
                "3.Get random crew member by role\n" +
                "0.Return");
        maxNumberOfOptions = 3;
        int input = handleUserInput();
        CrewMemberCriteria criteria;
        switch (input) {
            case 1:
                LOGGER.info("Handle user input {}. Get crew member by criteria.", input);
                System.out.println("Choose one or more number of criteria options (separate with comma!)\n");
                criteria = enterCriteria();
                CrewMember crewMember = crewMemberService.findCrewMemberByCriteria(criteria)
                        .orElse(null);
                if (crewMember == null) {
                    System.out.println("No such member");
                    break;
                }
                LOGGER.info("User get crew member {} by criteria", crewMember);
                System.out.println(crewMember);
                optionForUpdate(crewMember);
                LOGGER.info("Crew member {} successfully update", crewMember);
                printCrewMember();
                break;
            case 2:
                LOGGER.info("Handle user input {}. Get all crew members by criteria.", input);
                criteria = enterCriteria();
                crewMemberService.findAllCrewMembersByCriteria(criteria)
                        .forEach(System.out::println);
                break;
            case 3:
                LOGGER.info("Handle user input {}. Get random crew member by role.", input);
                Role role = enterRole();
                CrewMember randomCrewMember = crewMemberService.getRandomCrewMemberByRole(role);
                System.out.println(randomCrewMember);
                optionForUpdate(randomCrewMember);
                LOGGER.info("Crew member {} successfully update", randomCrewMember);
                break;
            case 0:
                LOGGER.info("Return");
                printAvailableOptions();
                break;
        }
    }

    private void optionForUpdate(CrewMember crewMember) {
        System.out.println("Do you want update crew member? (Y/N)");
        String answer = scanner.next();
        while (!answer.equals("Y") && !answer.equals("N")) {
            System.out.println("Incorrect input. Please, enter \"Y\" or \"N\"");
            answer = scanner.next();
        }
        switch (answer) {
            case "Y":
                LOGGER.info("Option for update {}", crewMember);
                System.out.println("Choose criteria for update\n");
                updateMember(crewMember);
                LOGGER.info("Crew member {} was successfully update", crewMember);
                break;
            case "N":
                break;
        }
    }

    private void updateMember(CrewMember crewMember) {
        System.out.println("Enter role");
        Role role = enterRole();
        System.out.println("Enter rank");
        Rank rank = enterRank();
        CrewMember newCrewMember = CrewMemberFactory.getInstance().create(role, crewMember.getName(), rank);
        crewMemberService.updateCrewMemberDetails(newCrewMember);
        System.out.println("Update successfully\n");
    }

    private CrewMemberCriteria enterCriteria() {
        System.out.println("1.Id\n" +
                "2.Name\n" +
                "3.Role\n" +
                "4.Rank");
        String options = enterOptionsOfCriteriaWithCheck();
        String[] numbersOfOptions = options.replaceAll("\\s", "").split(Separator.SPLIT_BY_COMA);
        return takeCriteria(numbersOfOptions);
    }

    private CrewMemberCriteria takeCriteria(String[] options) {
        long id = 0L;
        String name = null;
        Role role = null;
        Rank rank = null;
        for (String numberOfOption : options) {
            int number = Integer.parseInt(numberOfOption);
            switch (number) {
                case 1:
                    System.out.println("Enter id");
                    id = enterId();
                    break;
                case 2:
                    System.out.println("Enter name");
                    name = scanner.nextLine();
                    break;
                case 3:
                    System.out.println("Enter role");
                    role = enterRole();
                    break;
                case 4:
                    System.out.println("Enter rank");
                    rank = enterRank();
                    break;
            }
        }
        return CrewMemberCriteria.create()
                .whereId(id)
                .whereName(name)
                .whereRole(role)
                .whereRank(rank)
                .build();
    }

    private String enterOptionsOfCriteriaWithCheck() {
        String criteria = scanner.nextLine();
        String multiInput = "[1-4]((,[1-4])+)?";
        while (!criteria.matches(multiInput)) {
            System.out.println("Incorrect enter. Please try again");
            criteria = scanner.nextLine();
        }
        return criteria;
    }

    private long enterId() {
        long id;
        while (!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Incorrect input. Please, enter a number");
        }
        id = scanner.nextLong();
        return id;
    }

    private Rank enterRank() {
        System.out.println("Choose rank\n" +
                "1.Trainee\n" +
                "2.Second officer\n" +
                "3.First officer\n" +
                "4.Capitan");

        maxNumberOfOptions = 4;
        int choice = handleUserInput();
        return Rank.resolveRankById(choice);
    }

    private Role enterRole() {
        System.out.println("Choose role\n" +
                "1.Mission specialist\n" +
                "2.Flight engineer\n" +
                "3.Pilot\n" +
                "4.Commander");

        maxNumberOfOptions = 4;
        int choice = handleUserInput();
        return Role.resolveRoleById(choice);
    }
}
