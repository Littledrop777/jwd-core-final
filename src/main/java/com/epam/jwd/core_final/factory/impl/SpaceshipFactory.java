package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Arrays;
import java.util.Map;

public class SpaceshipFactory implements EntityFactory<Spaceship> {
    private static SpaceshipFactory instance;

    private SpaceshipFactory() {

    }

    public static SpaceshipFactory getInstance() {
        if (instance == null) {
            instance = new SpaceshipFactory();
        }
        return instance;
    }

    @Override
    public Spaceship create(Object... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(Error.INCORRECT_ARGUMENTS + Arrays.asList(args));
        }
        return new Spaceship((String) args[0], (Long) args[1], (Map<Role, Short>) args[2]);
    }

    @Override
    public Spaceship assignId(Spaceship entity, Long id) {
        return entity.withId(id);
    }


}
