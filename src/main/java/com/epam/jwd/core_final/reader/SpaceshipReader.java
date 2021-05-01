package com.epam.jwd.core_final.reader;

import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.SpaceshipFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.*;

public final class SpaceshipReader {

    private static SpaceshipReader instance;

    private SpaceshipReader() {
    }

    public static SpaceshipReader getInstance() {
        if (instance == null) {
            instance = new SpaceshipReader();
        }
        return instance;
    }

    private final ReaderFromFile readerFromFile = ReaderFromFile.getInstance();
    private final PropertyReaderUtil propertyReader = PropertyReaderUtil.getInstance();
    private final String path = propertyReader.getInputRootDir() + Separator.FILE_SEPARATOR + propertyReader.getSpaceshipsFileName();

    public Collection<Spaceship> initSpaceship() throws InvalidStateException {
        Collection<Spaceship> spaceships = new ArrayList<>();
        List<String> spaceshipsInfo = readerFromFile.readFileByLine(path);

        String[] args;
        for (String info : spaceshipsInfo) {
            if (!info.matches(PatternsForValidation.SPACESHIP)) {
                throw new InvalidStateException(Error.INCORRECT_DATA + info);
            }
            args = info.split(Separator.SPLIT_BY_SEMICOLON);
            spaceships.add(SpaceshipFactory.getInstance()
                    .create(args[0], Long.valueOf(args[1]), createMap(args[2])));
        }
        return spaceships;
    }

    private Map<Role, Short> createMap(String data) {
        Map<Role, Short> crew = new HashMap<>();
        String[] array = data.replaceAll("[{|}]", "").split(",");

        for (String temp : array) {
            String[] roleAndAmount = temp.split(":");
            crew.put(Role.resolveRoleById(Integer.parseInt(roleAndAmount[0])), Short.valueOf(roleAndAmount[1]));
        }
        return crew;
    }
}
