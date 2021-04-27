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

    CrewMember addCrewMember(CrewMember crewMember);

    Spaceship addSpaceship(Spaceship spaceship);

    Planet addPlanet(Planet planet);

    FlightMission addFlightMission(FlightMission flightMission);

}
