package com.epam.jwd.core_final.domain;

import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * location could be a simple class Point with 2 coordinates
 */
public class Planet extends AbstractBaseEntity{
    private final Point location;

    public Planet(String name, Point location) {
        super(name);
        this.location = location;
    }
    public Planet(long id, String name, Point location) {
        super(id, name);
        this.location = location;
    }
    public Planet withId(long id) {
        return new Planet(id, getName(), location);
    }

    public Point getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(location, planet.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @Override
    public String toString() {
        return "Planet " + getName() +
                " location(" + location.getX()+',' + location.getY()+
                ')';
    }
}
