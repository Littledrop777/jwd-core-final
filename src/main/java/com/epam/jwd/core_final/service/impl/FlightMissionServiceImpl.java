package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.service.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public enum FlightMissionServiceImpl implements MissionService {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(FlightMissionServiceImpl.class);
    private final NassaContext context;
    private final Collection<FlightMission> flightMissions;

    {
        context = NassaContext.getInstance();
        flightMissions = context.retrieveBaseEntityList(FlightMission.class);
    }

    @Override
    public List<FlightMission> findAllMissions() {
        return new ArrayList<>(flightMissions);
    }

    @Override
    public List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria) {
        if (flightMissions.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Collections.emptyList();
        }
        return flightMissions.stream()
                .filter(mission -> match(mission, criteria))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria) {
        if (flightMissions.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Optional.empty();
        }
        return findAllMissionsByCriteria(criteria).stream().findFirst();

    }

    @Override
    public FlightMission updateMission(FlightMission flightMission) throws RuntimeException {
        if (Objects.isNull(flightMission) || flightMissions.stream()
                .noneMatch(existMission -> existMission.getId().equals(flightMission.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        context.addFlightMission(flightMission);
        return flightMission;
    }

    @Override
    public FlightMission createMission(FlightMission flightMission) throws RuntimeException {
        if (Objects.isNull(flightMission)) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (flightMissions.stream()
                .anyMatch(existMission -> existMission.getId().equals(flightMission.getId()))) {
            throw new EntityCreationException(Error.ENTITY_IS_ALREADY_EXIST + flightMission.getName());
        }
        context.addFlightMission(flightMission);
        return flightMission;
    }

    private boolean match(FlightMission flightMission, FlightMissionCriteria criteria) {
        List<Boolean> checkList = new ArrayList<>();

        if (criteria.getId() != 0L) {
            checkList.add(criteria.getId() == flightMission.getId());
        }
        if (criteria.getAssignedCrew() != null) {
            checkList.add(Objects.equals(criteria.getAssignedCrew(), flightMission.getAssignedCrew()));
        }
        if (criteria.getName() != null) {
            checkList.add(Objects.equals(criteria.getName(), flightMission.getName()));
        }
        if (criteria.getAssignedCrew() != null) {
            checkList.add(Objects.equals(criteria.getAssignedCrew(), flightMission.getAssignedCrew()));
        }
        if (criteria.getDistance() != 0L) {
            checkList.add(criteria.getDistance() == flightMission.getDistance());
        }
        if (criteria.getAssignedSpaceShip() != null) {
            checkList.add(Objects.equals(criteria.getAssignedSpaceShip(), flightMission.getAssignedSpaceShip()));
        }
        if (criteria.getFrom() != null) {
            checkList.add(Objects.equals(criteria.getFrom(), flightMission.getFrom()));
        }
        if (criteria.getTo() != null) {
            checkList.add(Objects.equals(criteria.getTo(), flightMission.getTo()));
        }
        if (criteria.getStartDate() != null) {
            checkList.add(Objects.equals(criteria.getStartDate(), flightMission.getStartDate()));
        }
        if (criteria.getEndDate() != null) {
            checkList.add(Objects.equals(criteria.getEndDate(), flightMission.getEndDate()));
        }
        if (checkList.isEmpty()) {
            return false;
        }
        return !checkList.contains(false);
    }
}
