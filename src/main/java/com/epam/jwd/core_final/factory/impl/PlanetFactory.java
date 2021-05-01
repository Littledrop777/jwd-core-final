package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.domain.Point;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Arrays;

public class PlanetFactory implements EntityFactory<Planet> {

    private static PlanetFactory instance;

    private PlanetFactory() {
    }

    public static PlanetFactory getInstance() {
        if (instance == null) {
            instance = new PlanetFactory();
        }
        return instance;
    }

    @Override
    public Planet create(Object... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Incorrect arguments " + Arrays.asList(args));
        }
        Point location = new Point((int) args[1], (int) args[2]);
        return new Planet((String) args[0], location);
    }

    @Override
    public Planet assignId(Planet entity, Long id) {
        return entity.withId(id);
    }
}
