package com.epam.jwd.core_final.reader;

import com.epam.jwd.core_final.domain.Planet;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.PlanetFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class SpaceMapReader {

    public static final String NOT_A_PLANET = "null";
    private static SpaceMapReader instance;

    private SpaceMapReader() {
    }

    public static SpaceMapReader getInstance() {
        if (instance == null) {
            instance = new SpaceMapReader();
        }
        return instance;
    }

    private final ReaderFromFile readerFromFile = ReaderFromFile.getInstance();
    private final PropertyReaderUtil propertyReader = PropertyReaderUtil.getInstance();
    private final String path = propertyReader.getInputRootDir() + Separator.FILE_SEPARATOR + propertyReader.getSpacemapFileName();

    public Collection<Planet> initPlanets() throws InvalidStateException {
        Collection<Planet> planets = new ArrayList<>();
        List<String> spaceMapLines = readerFromFile.readFileByLine(path);

        String[] args;
        for (int y = 0; y < spaceMapLines.size(); y++) {
            args = spaceMapLines.get(y).trim().split(Separator.SPLIT_BY_COMA);
            for (int x = 0; x < args.length; x++) {
                if (!args[x].trim().equals(NOT_A_PLANET)) {
                    planets.add(PlanetFactory.getInstance().create(args[x], x, y));
                }
            }
        }
        return planets;
    }
}
