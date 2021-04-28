package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityAssignedException;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class CrewMemberServiceImpl implements CrewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrewMemberServiceImpl.class);

    private static CrewMemberServiceImpl instance;
    private final Collection<CrewMember> crewMembers;
    private static final NassaContext context = NassaContext.getInstance();

    {
        crewMembers = context.retrieveBaseEntityList(CrewMember.class);
    }

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
        if (crewMembers.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Collections.emptyList();
        }
        return crewMembers.stream()
                .filter(crewMember -> match(crewMember, criteria))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<CrewMember> findCrewMemberByCriteria(CrewMemberCriteria criteria) {
        if (crewMembers.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Optional.empty();
        }
        return findAllCrewMembersByCriteria(criteria).stream().findFirst();
    }

    @Override
    public CrewMember updateCrewMemberDetails(CrewMember crewMember) {
        if (Objects.isNull(crewMember) || crewMembers.stream()
                .noneMatch(existCrewMember -> existCrewMember.getId().equals(crewMember.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        CrewMember updateCrewMember;
        if (crewMember.isReadyForNextMission()) {
            updateCrewMember = crewMember.readyForNextMission(false);
        }else{
            updateCrewMember = crewMember.readyForNextMission(true);
        }
        context.removeCrewMember(crewMember);
        context.addCrewMember(updateCrewMember);
        return updateCrewMember;
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        if (Objects.isNull(crewMember) || crewMembers.stream()
                .noneMatch(existCrewMember -> existCrewMember.getId().equals(crewMember.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (crewMember.isReadyForNextMission()) {
           updateCrewMemberDetails(crewMember);
        }else{
            throw new EntityAssignedException(Error.ENTITY_CAN_NOT_TO_BE_ASSIGNED + crewMember.getName());
        }
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

    private boolean match(CrewMember crewMember, CrewMemberCriteria criteria) {
        List<Boolean> checkList = new ArrayList<>();

        if (criteria.getRank() != null) {
            checkList.add(Objects.equals(criteria.getRank(), crewMember.getRank()));
        }
        if (criteria.getId() != 0L) {
            checkList.add(criteria.getId() == crewMember.getId());
        }
        if (criteria.getName() != null) {
            checkList.add(Objects.equals(criteria.getName(), crewMember.getName()));
        }
        if (criteria.getRole() != null) {
            checkList.add(Objects.equals(criteria.getRole(), crewMember.getRole()));
        }
        if (checkList.isEmpty()) {
            return false;
        }
        return !checkList.contains(false);
    }

}
