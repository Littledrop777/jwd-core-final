package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.factory.EntityFactory;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.reader.CrewReader;
import com.epam.jwd.core_final.reader.SpaceMapReader;
import com.epam.jwd.core_final.reader.SpaceshipReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


public final class NassaContext implements ApplicationContext {

    private static NassaContext instance;
    private static long maxId = 0L;

    private NassaContext() {
    }

    public static NassaContext getInstance() {
        if (instance == null) {
            instance = new NassaContext();
        }
        return instance;
    }

    // no getters/setters for them
    private final Collection<CrewMember> crewMembers = new ArrayList<>();
    private final Collection<Spaceship> spaceships = new ArrayList<>();
    private final Collection<Planet> planetMap = new ArrayList<>();
    private final Collection<FlightMission> flightMissions = new ArrayList<>();

    @Override
    public <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass) {

        if (CrewMember.class.equals(tClass)) {
            return (Collection<T>) crewMembers;
        } else if (Spaceship.class.equals(tClass)) {
            return (Collection<T>) spaceships;
        } else if (Planet.class.equals(tClass)) {
            return (Collection<T>) planetMap;
        } else if (FlightMission.class.equals(tClass)) {
            return (Collection<T>) flightMissions;
        }
        throw new UnknownEntityException(Error.INCORRECT_DATA + tClass.getSimpleName());
    }

    /**
     * You have to read input files, populate collections
     *
     * @throws InvalidStateException
     */
    @Override
    public void init() throws InvalidStateException {
        CrewReader.getInstance().initCrewMember().forEach(this::addCrewMember);
        SpaceMapReader.getInstance().initPlanets().forEach(this::addPlanet);
        SpaceshipReader.getInstance().initSpaceship().forEach(this::addSpaceship);
    }

    @Override
    public CrewMember addCrewMember(CrewMember crewMember) {
        if (Objects.isNull(crewMember)) {
            throw new UnknownEntityException(Error.ENTITY_DOES_NOT_EXIST);
        }
        CrewMember crewMemberWithId = chooseAppropriateEntityFactory(crewMember).assignId(crewMember, ++maxId);
        crewMembers.add(crewMemberWithId);
        return crewMemberWithId;
    }

    @Override
    public Spaceship addSpaceship(Spaceship spaceship) {
        if (Objects.isNull(spaceship)) {
            throw new UnknownEntityException(Error.ENTITY_DOES_NOT_EXIST);
        }
        Spaceship spaceshipWithId = chooseAppropriateEntityFactory(spaceship).assignId(spaceship, ++maxId);
        spaceships.add(spaceshipWithId);
        return spaceshipWithId;
    }

    @Override
    public Planet addPlanet(Planet planet) {
        if (Objects.isNull(planet)) {
            throw new UnknownEntityException(Error.ENTITY_DOES_NOT_EXIST);
        }
        Planet planetWithId = chooseAppropriateEntityFactory(planet).assignId(planet, ++maxId);
        planetMap.add(planetWithId);
        return planetWithId;
    }

    @Override
    public FlightMission addFlightMission(FlightMission flightMission) {
        if (Objects.isNull(flightMission)) {
            throw new UnknownEntityException(Error.ENTITY_DOES_NOT_EXIST);
        }
        FlightMission flightMissionWithId = FlightMission.construct()
                .setId(++maxId)
                .setName(flightMission.getName())
                .setMissionResult(flightMission.getMissionResult())
                .setDistance(flightMission.getDistance())
                .setEndDate(flightMission.getEndDate())
                .setStartDate(flightMission.getStartDate())
                .setAssignedCrew(flightMission.getAssignedCrew())
                .setAssignedSpaceShip(flightMission.getAssignedSpaceShip())
                .setFrom(flightMission.getFrom())
                .setTo(flightMission.getTo())
                .build();

        flightMissions.add(flightMissionWithId);
        return flightMissionWithId;
    }


    private <T extends BaseEntity> EntityFactory<T> chooseAppropriateEntityFactory(T entity) {
        if (entity.getClass().getSimpleName().equals(CrewMember.class.getSimpleName())) {
            return (EntityFactory<T>) CrewMemberFactory.getInstance();
        } else if (entity.getClass().getSimpleName().equals(Spaceship.class.getSimpleName())) {
            return (EntityFactory<T>) SpaceshipFactory.getInstance();
        } else if (entity.getClass().getSimpleName().equals(Planet.class.getSimpleName())) {
            return (EntityFactory<T>) PlanetFactory.getInstance();
        }
        throw new UnknownEntityException(Error.INCORRECT_DATA + entity.getClass().getSimpleName());
    }

}
