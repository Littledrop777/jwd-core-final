package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.Collection;

public interface ApplicationContext {

    <T extends BaseEntity> Collection<T> retrieveBaseEntityList(Class<T> tClass);

    void init() throws InvalidStateException;

    void addCrewMember(CrewMember crewMember);

    void addSpaceship(Spaceship spaceship);

    void addPlanet(Planet planet);

    void addFlightMission(FlightMission flightMission);

    void removeCrewMember(CrewMember crewMember);

    void removeSpaceship(Spaceship spaceship);

    void removePlanet(Planet planet);

    void removeFlightMission(FlightMission flightMission);

}
