package com.epam.jwd.core_final.reader;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.factory.impl.CrewMemberFactory;
import com.epam.jwd.core_final.util.PropertyReaderUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class CrewReader {

    private static CrewReader instance;

    private CrewReader() {
    }

    public static CrewReader getInstance() {
        if (instance == null) {
            instance = new CrewReader();
        }
        return instance;
    }

    private final ReaderFromFile readerFromFile = ReaderFromFile.getInstance();
    private final PropertyReaderUtil propertyReader = PropertyReaderUtil.getInstance();
    private final String path = propertyReader.getInputRootDir() + Separator.FILE_SEPARATOR + propertyReader.getCrewFileName();

    public Collection<CrewMember> initCrewMember() throws InvalidStateException {
        Collection<CrewMember> crewMembers = new ArrayList<>();
        List<String> crewMemberInfo = readerFromFile.readFileByLine(path).stream()
                .flatMap(l -> Arrays.stream(l.split(Separator.SPLIT_BY_ENTITY)))
                .collect(Collectors.toList());

        String[] args;
        for (String info : crewMemberInfo) {
            if (!info.matches(PatternsForValidation.CREW_MEMBER)) {
                throw new InvalidStateException(Error.INCORRECT_DATA + info);
            }
            args = info.split(Separator.SPLIT_BY_INFO);
            CrewMember crewMember = CrewMemberFactory.getInstance()
                    .create(Integer.valueOf(args[0]), args[1], Integer.valueOf(args[2]));
            crewMembers.add(crewMember);
        }

        return crewMembers;
    }

}