package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public enum SpaceMapServiceImpl implements SpacemapService {
    INSTANCE;

    private final Collection<Planet> planets;

    {
        NassaContext context = NassaContext.getInstance();
        planets = context.retrieveBaseEntityList(Planet.class);
    }

    @Override
    public Planet getRandomPlanet() {
        Random random = new Random();
        List<Planet> listPlanets = new ArrayList<>(planets);
        return listPlanets.get(random.nextInt(planets.size()));
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        double result;
        double oneSide;
        double secondSide;

        oneSide = Math.pow((second.getLocation().getX() - first.getLocation().getX()), 2);
        secondSide = Math.pow((second.getLocation().getY() - first.getLocation().getY()), 2);
        result = Math.sqrt(oneSide + secondSide);

        return (int) result;
    }
}
