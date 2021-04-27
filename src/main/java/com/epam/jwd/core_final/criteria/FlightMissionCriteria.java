package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Spaceship;

import java.time.LocalDate;
import java.util.List;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.FlightMission} fields
 */
public class FlightMissionCriteria extends Criteria<FlightMission> {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long distance;
    private final Spaceship assignedSpaceship;
    private final List<CrewMember> assignedCrew;
    private final Planet from;
    private final Planet to;

    private FlightMissionCriteria(long id, String name, LocalDate startDate, LocalDate endDate, long distance, Spaceship assignedSpaceship, List<CrewMember> assignedCrew, Planet from, Planet to) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceship = assignedSpaceship;
        this.assignedCrew = assignedCrew;
        this.from = from;
        this.to = to;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceship;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    public static CriteriaBuilder create() {
        return new CriteriaBuilder();
    }

    public static class CriteriaBuilder {
        private String name;
        private long id;
        private LocalDate startDate;
        private LocalDate endDate;
        private long distance;
        private Spaceship assignedSpaceship;
        private List<CrewMember> assignedCrew;
        private Planet from;
        private Planet to;

        public CriteriaBuilder() {
        }

        public CriteriaBuilder nameIs(String name) {
            this.name = name;
            return this;
        }

        public CriteriaBuilder idIs(long id) {
            this.id = id;
            return this;
        }

        public CriteriaBuilder whereStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CriteriaBuilder whereEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;

        }

        public CriteriaBuilder distanceIs(long distance) {
            this.distance = distance;
            return this;
        }

        public CriteriaBuilder assignedSpaceship(Spaceship spaceship) {
            this.assignedSpaceship = spaceship;
            return this;
        }

        public CriteriaBuilder assignedCrew(List<CrewMember> crew) {
            this.assignedCrew = crew;
            return this;
        }

        public CriteriaBuilder wherePlanetFrom(Planet from) {
            this.from = from;
            return this;
        }

        public CriteriaBuilder wherePlanetTo(Planet to) {
            this.to = to;
            return this;
        }

        public FlightMissionCriteria build() {
            return new FlightMissionCriteria(id, name, startDate, endDate, distance, assignedSpaceship, assignedCrew, from, to);
        }
    }
}
