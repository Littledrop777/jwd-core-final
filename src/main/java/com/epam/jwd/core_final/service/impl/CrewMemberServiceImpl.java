package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.service.CrewService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class CrewMemberServiceImpl implements CrewService {

    private static CrewMemberServiceImpl instance;
    private final Collection<CrewMember> crewMembers = NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);

    private CrewMemberServiceImpl() {
    }

    public static CrewMemberServiceImpl getInstance() {
        if (instance == null) {
            instance = new CrewMemberServiceImpl();
        }
        return instance;
    }

    @Override
    public List<CrewMember> findAllCrewMembers() {
        return new ArrayList<>(crewMembers);
    }

    @Override
    public List<CrewMember> findAllCrewMembersByCriteria(CrewMemberCriteria criteria) {
        return null;
    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria) {
        return Optional.empty();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        if (Objects.isNull(crewMember)) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        Optional<CrewMember> checkCrewMember = crewMembers.stream()
                .filter(existCrewMember -> existCrewMember.getId().equals(crewMember.getId()))
                .findFirst();

        if (!checkCrewMember.isPresent()) {
            throw new EntityCreationException(Error.ENTITY_IS_ALREADY_EXIST + crewMember.getName());
        }

        crewMembers.remove(checkCrewMember.get());
        crewMembers.add(crewMember);
        return crewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {

    }

    @Override
    public CrewMember createCrewMember(CrewMember crewMember) throws RuntimeException {
        if (Objects.isNull(crewMember)) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (crewMembers.stream()
                .anyMatch(existCrewMember -> existCrewMember.getId().equals(crewMember.getId()))) {
            throw new EntityCreationException(Error.ENTITY_IS_ALREADY_EXIST + crewMember.getName());
        }
        crewMembers.add(crewMember);
        return crewMember;
    }

}
