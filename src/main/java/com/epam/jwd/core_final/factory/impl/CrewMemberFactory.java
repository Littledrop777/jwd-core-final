package com.epam.jwd.core_final.factory.impl;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.factory.EntityFactory;

import java.util.Arrays;

// do the same for other entities
public class CrewMemberFactory implements EntityFactory<CrewMember> {

    private static CrewMemberFactory instance;

    private CrewMemberFactory() {
    }

    public static CrewMemberFactory getInstance() {
        if (instance == null) {
            instance = new CrewMemberFactory();
        }
        return instance;
    }

    @Override
    public CrewMember create(Object... args) {
        if (args.length != 3) {
            throw new IllegalArgumentException(Error.INCORRECT_ARGUMENTS + Arrays.asList(args));
        }
        return new CrewMember((Role) args[0], (String) args[1], (Rank) args[2]);
    }

    @Override
    public CrewMember assignId(CrewMember entity, Long id) {
        return entity.withId(id);
    }
}
