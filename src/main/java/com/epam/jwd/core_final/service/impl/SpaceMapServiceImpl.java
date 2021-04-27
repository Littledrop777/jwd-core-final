package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.service.SpacemapService;

public enum SpaceMapServiceImpl implements SpacemapService {
    INSTANCE;

    @Override
    public Planet getRandomPlanet() {
        return null;
    }

    @Override
    public int getDistanceBetweenPlanets(Planet first, Planet second) {
        return 0;
    }
}
