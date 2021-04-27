package com.epam.jwd.core_final.domain;

import java.util.Map;
import java.util.Objects;

/**
 * crew {@link java.util.Map<Role, Short>}
 * flightDistance {@link Long} - total available flight distance
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class Spaceship extends AbstractBaseEntity {

    private final Map<Role, Short> crew;
    private final long flightDistance;
    private boolean isReadyForNextMission = true;

    public Spaceship(String name, long flightDistance, Map<Role, Short> crew) {
        super(name);
        this.flightDistance = flightDistance;
        this.crew = crew;
    }
    public Spaceship(long id, String name, long flightDistance, Map<Role, Short> crew) {
        super(id, name);
        this.flightDistance = flightDistance;
        this.crew = crew;
    }

    public Spaceship withId(long id) {
        return new Spaceship(id, getName(), flightDistance, crew);
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public long getFlightDistance() {
        return flightDistance;
    }

    public boolean isReadyForNextMission() {
        return isReadyForNextMission;
    }

    public void setReadyForNextMission(boolean readyForNextMission) {
        isReadyForNextMission = readyForNextMission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spaceship spaceship = (Spaceship) o;
        return flightDistance == spaceship.flightDistance && isReadyForNextMission == spaceship.isReadyForNextMission && Objects.equals(crew, spaceship.crew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crew, flightDistance, isReadyForNextMission);
    }

    @Override
    public String toString() {
        return "Spaceship{" +
                "crew=" + crew +
                ", flightDistance=" + flightDistance +
                ", isReadyForNextMission=" + isReadyForNextMission +
                "} ";
    }
}
