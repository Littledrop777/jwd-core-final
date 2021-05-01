package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityAssignedException;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.service.CrewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public final class CrewMemberServiceImpl implements CrewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrewMemberServiceImpl.class);
    private static CrewMemberServiceImpl instance;
    private final List<CrewMember> crewMembers;
    private final NassaContext context;

    {
        context = NassaContext.getInstance();
        crewMembers = (List<CrewMember>) context.retrieveBaseEntityList(CrewMember.class);
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
        if (crewMembers.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Collections.emptyList();
        }
        return crewMembers;
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
    public CrewMember updateCrewMemberDetails(CrewMember newCrewMember) {
        if (Objects.isNull(newCrewMember) || crewMembers.stream()
                .noneMatch(existCrewMember -> existCrewMember.getName().equals(newCrewMember.getName()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        CrewMember crewMember = crewMembers.stream()
                .filter(member -> member.getName().equals(newCrewMember.getName()))
                .findFirst()
                .orElse(newCrewMember);

        context.removeCrewMember(crewMember);
        return context.addCrewMember(newCrewMember);
    }

    @Override
    public void assignCrewMemberOnMission(CrewMember crewMember) throws RuntimeException {
        if (Objects.isNull(crewMember) || crewMembers.stream()
                .noneMatch(existCrewMember -> existCrewMember.getId().equals(crewMember.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (crewMember.isReadyForNextMission()) {
            crewMember.readyForNextMission(false);
            updateCrewMemberDetails(crewMember);
        } else {
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
        return context.addCrewMember(crewMember);
    }

    @Override
    public CrewMember getRandomCrewMemberByRole(Role role) {
        Random random = new Random();
        List<CrewMember> crewMembersByRole = crewMembers.stream()
                .filter(member -> member.getRole().equals(role) && member.isReadyForNextMission())
                .collect(Collectors.toList());
        return crewMembersByRole.get(random.nextInt(crewMembersByRole.size()));
    }

    @Override
    public List<CrewMember> generateCrewForSpaceship(Spaceship spaceship) {
        List<CrewMember> crew = new ArrayList<>();
        for (Role role : spaceship.getCrew().keySet()) {
            short amount = spaceship.getCrew().get(role);
            for (int i = 0; i <= amount; i++) {
                CrewMember crewMember = getRandomCrewMemberByRole(role);
                crew.add(crewMember);
            }
        }
        return crew;
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
