package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.CrewMemberServiceImpl;
import com.epam.jwd.core_final.service.impl.DateGenerator;
import com.epam.jwd.core_final.service.impl.SpaceMapServiceImpl;
import com.epam.jwd.core_final.service.impl.SpaceshipServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    private static FlightMissionFactory instance;

    private FlightMissionFactory() {
    }

    public static FlightMissionFactory getInstance() {
        if (instance == null) {
            instance = new FlightMissionFactory();
        }
        return instance;
    }

    @Override
    public FlightMission create(Object... args) {
        if (args.length != 6) {
            throw new IllegalArgumentException(Error.INCORRECT_ARGUMENTS + Arrays.asList(args));
        }
        String name = (String) args[0];
        Planet from = (Planet) args[1];
        Planet to = (Planet) args[2];
        Spaceship spaceship = (Spaceship) args[3];
        LocalDate start = LocalDate.parse((String) args[4]);
        LocalDate end = LocalDate.parse((String) args[5]);
        long distance = SpaceMapServiceImpl.INSTANCE.getDistanceBetweenPlanets(from, to);
        List<CrewMember> crew = CrewMemberServiceImpl.getInstance().generateCrewForSpaceship(spaceship);

        return FlightMission.construct()
                .setName(name)
                .setFrom(from)
                .setTo(to)
                .setAssignedSpaceShip(spaceship)
                .setStartDate(start)
                .setEndDate(end)
                .setDistance(distance)
                .setAssignedCrew(crew)
                .setMissionResult(MissionResult.PLANNED)
                .build();
    }

    @Override
    public FlightMission assignId(FlightMission entity, Long id) {
        return FlightMission.construct()
                .setId(id)
                .setName(entity.getName())
                .setMissionResult(entity.getMissionResult())
                .setDistance(entity.getDistance())
                .setEndDate(entity.getEndDate())
                .setStartDate(entity.getStartDate())
                .setAssignedCrew(entity.getAssignedCrew())
                .setAssignedSpaceShip(entity.getAssignedSpaceShip())
                .setFrom(entity.getFrom())
                .setTo(entity.getTo())
                .build();
    }

    public FlightMission generateMission(String name) {
        Spaceship spaceship = SpaceshipServiceImpl.getInstance().getRandomSpaceship();
        List<CrewMember> crew = CrewMemberServiceImpl.getInstance().generateCrewForSpaceship(spaceship);
        Planet from = SpaceMapServiceImpl.INSTANCE.getRandomPlanet();
        Planet to = SpaceMapServiceImpl.INSTANCE.getRandomPlanet();

        while (from.equals(to)) {
            to = SpaceMapServiceImpl.INSTANCE.getRandomPlanet();
        }
        long distance = SpaceMapServiceImpl.INSTANCE.getDistanceBetweenPlanets(from, to);

        return FlightMission.construct()
                .setName(name)
                .setAssignedSpaceShip(spaceship)
                .setAssignedCrew(crew)
                .setDistance(distance)
                .setStartDate(LocalDate.now())
                .setEndDate(DateGenerator.INSTANCE.generateDate())
                .setFrom(from)
                .setTo(to)
                .setMissionResult(MissionResult.PLANNED)
                .build();
    }
}
