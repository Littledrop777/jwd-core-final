package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum MissionGenerator {
    INSTANCE;

    private final LocalDate date = LocalDate.now();
    private final String dateFormat = PropertyReaderUtil.getInstance().getDateTimeFormat();
    private final NassaContext context = NassaContext.getInstance();
    private final List<CrewMember> allCrewMembers;
    private final List<Spaceship> allSpaceships;
    private final List<Planet> allPlanets;

    {
        allCrewMembers = (List<CrewMember>) context.retrieveBaseEntityList(CrewMember.class);
        allSpaceships = (List<Spaceship>) context.retrieveBaseEntityList(Spaceship.class);
        allPlanets = (List<Planet>) context.retrieveBaseEntityList(Planet.class);
    }

    private final Random random = new Random();


    public LocalDate generateDate() {
        int maxDays = 15;
        return date.plusDays(random.nextInt(maxDays) + 1);
    }

    public Spaceship generateSpaceship() {
        Spaceship spaceship = allSpaceships.get(random.nextInt(allSpaceships.size()));
        while (!spaceship.isReadyForNextMission()) {
            spaceship = allSpaceships.get(random.nextInt(allSpaceships.size()));
        }
        return spaceship;
    }

    public List<CrewMember> generateCrewForSpaceship(Spaceship spaceship) {
        List<CrewMember> crew = new ArrayList<>();
        for (Role role : spaceship.getCrew().keySet()) {
            short amount = spaceship.getCrew().get(role);
            for (int i = 0; i <= amount; i++) {
                CrewMember crewMember = generateCrewMember(role);
                while (!crewMember.isReadyForNextMission()) {
                    crewMember = generateCrewMember(role);
                }
                crew.add(crewMember);
            }
        }
        return crew;
    }

    public Planet generatePlanet() {
        return allPlanets.get(random.nextInt(allPlanets.size()));
    }

    public CrewMember generateCrewMember(Role role) {
        List<CrewMember> crewMembers = allCrewMembers.stream()
                .filter(crewMember -> crewMember.getRole().equals(role))
                .collect(Collectors.toList());

        return crewMembers.get(random.nextInt(crewMembers.size()));
    }

}
