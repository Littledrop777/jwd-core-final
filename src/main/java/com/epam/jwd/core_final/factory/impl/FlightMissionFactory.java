package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.context.impl.MissionGenerator;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.MissionResult;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.service.impl.SpaceMapServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;

public class FlightMissionFactory implements EntityFactory<FlightMission> {

    private static FlightMissionFactory instance;
    private final MissionGenerator generator = MissionGenerator.INSTANCE;

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

        return FlightMission.construct()
                .setName(name)
                .setFrom(from)
                .setTo(to)
                .setDistance(distance)
                .setStartDate(start)
                .setEndDate(end)
                .setAssignedSpaceShip(spaceship)
                .setAssignedCrew(generator.generateCrewForSpaceship(spaceship))
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
        Planet from = generator.generatePlanet();
        Planet to = generator.generatePlanet();

        while (from.equals(to)) {
            to = generator.generatePlanet();
        }
        Spaceship spaceship = generator.generateSpaceship();
        long distance = SpaceMapServiceImpl.INSTANCE.getDistanceBetweenPlanets(from, to);

        return FlightMission.construct()
                .setName(name)
                .setMissionResult(MissionResult.PLANNED)
                .setDistance(distance)
                .setStartDate(LocalDate.now())
                .setEndDate(generator.generateDate())
                .setAssignedSpaceShip(spaceship)
                .setAssignedCrew(generator.generateCrewForSpaceship(spaceship))
                .setFrom(from)
                .setTo(to)
                .build();
    }
}
