package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;

import java.util.Map;

/**
 * Should be a builder for {@link Spaceship} fields
 */
public class SpaceshipCriteria extends Criteria<Spaceship> {

    private final Map<Role, Short> crew;
    private final long flightDistance;

    private SpaceshipCriteria(long id, String name, Map<Role, Short> crew, long flightDistance) {
        super(id,name);
        this.crew = crew;
        this.flightDistance = flightDistance;
    }

    public Map<Role, Short> getCrew() {
        return crew;
    }

    public long getFlightDistance() {
        return flightDistance;
    }

    public static CriteriaBuilder create() {
        return new CriteriaBuilder();
    }


    public static class CriteriaBuilder {
        private String name;
        private long id;
        private Map<Role, Short> crew;
        private long flightDistance;

        public CriteriaBuilder() {
        }

        public CriteriaBuilder whereName(String name) {
            this.name = name;
            return this;
        }

        public CriteriaBuilder whereId(long id) {
            this.id = id;
            return this;
        }

        public CriteriaBuilder whereCrew(Map<Role, Short> crew) {
            this.crew = crew;
            return this;
        }

        public CriteriaBuilder whereFlightDistance(long flightDistance) {
            this.flightDistance = flightDistance;
            return this;
        }

        public SpaceshipCriteria build() {
            return new SpaceshipCriteria(id, name, crew, flightDistance);
        }
    }
}
