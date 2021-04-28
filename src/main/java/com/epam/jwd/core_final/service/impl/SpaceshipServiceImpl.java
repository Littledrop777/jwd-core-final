package com.epam.jwd.core_final.service.impl;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.criteria.SpaceshipCriteria;
import com.epam.jwd.core_final.domain.Spaceship;
import com.epam.jwd.core_final.exception.EntityAssignedException;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.Error;
import com.epam.jwd.core_final.service.SpaceshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class SpaceshipServiceImpl implements SpaceshipService {

    private static SpaceshipServiceImpl instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(CrewMemberServiceImpl.class);
    private final Collection<Spaceship> spaceships;
    private final NassaContext context;

    {
        context = NassaContext.getInstance();
        spaceships = context.retrieveBaseEntityList(Spaceship.class);
    }

    private SpaceshipServiceImpl() {
    }

    public static SpaceshipServiceImpl getInstance() {
        if (instance == null) {
            instance = new SpaceshipServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Spaceship> findAllSpaceships() {
        return new ArrayList<>(spaceships);
    }

    @Override
    public List<Spaceship> findAllSpaceshipsByCriteria(SpaceshipCriteria criteria) {
        if (spaceships.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Collections.emptyList();
        }
        return spaceships.stream()
                .filter(spaceship -> match(spaceship, criteria))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Spaceship> findSpaceshipByCriteria(SpaceshipCriteria criteria) {
        if (spaceships.isEmpty()) {
            LOGGER.error(Error.STORAGE_IS_EMPTY);
            return Optional.empty();
        }
        return findAllSpaceshipsByCriteria(criteria).stream().findFirst();

    }

    @Override
    public Spaceship updateSpaceshipDetails(Spaceship spaceship) {
        if (Objects.isNull(spaceship) || spaceships.stream()
                .noneMatch(existSpaceship -> existSpaceship.getId().equals(spaceship.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        Spaceship updateSpaceship;
        if (spaceship.isReadyForNextMission()) {
            updateSpaceship = spaceship.readyForNextMission(false);
        }else{
            updateSpaceship = spaceship.readyForNextMission(true);
        }
        context.removeSpaceship(spaceship);
        context.addSpaceship(updateSpaceship);

        return updateSpaceship;
    }

    @Override
    public void assignSpaceshipOnMission(Spaceship spaceship) throws RuntimeException {
        if (Objects.isNull(spaceship) || spaceships.stream()
                .noneMatch(existSpaceship -> existSpaceship.getId().equals(spaceship.getId()))) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (spaceship.isReadyForNextMission()) {
            updateSpaceshipDetails(spaceship);
        }else{
            throw new EntityAssignedException(Error.ENTITY_CAN_NOT_TO_BE_ASSIGNED + spaceship.getName());
        }
    }

    @Override
    public Spaceship createSpaceship(Spaceship spaceship) throws RuntimeException {
        if (Objects.isNull(spaceship)) {
            throw new EntityCreationException(Error.ENTITY_DOES_NOT_EXIST);
        }
        if (spaceships.stream()
                .anyMatch(existSpaceship -> existSpaceship.getId().equals(spaceship.getId()))) {
            throw new EntityCreationException(Error.ENTITY_IS_ALREADY_EXIST + spaceship.getName());
        }
        context.addSpaceship(spaceship);
        return spaceship;
    }

    private boolean match(Spaceship spaceship, SpaceshipCriteria criteria) {
        List<Boolean> checkList = new ArrayList<>();

        if (criteria.getId() != 0L) {
            checkList.add(criteria.getId() == spaceship.getId());
        }
        if (criteria.getName() != null) {
            checkList.add(Objects.equals(criteria.getName(), spaceship.getName()));
        }
        if (criteria.getCrew() != null) {
            checkList.add(Objects.equals(criteria.getCrew(), spaceship.getCrew()));
        }
        if (criteria.getFlightDistance() != 0L) {
            checkList.add(criteria.getFlightDistance() == spaceship.getFlightDistance());
        }
        if (checkList.isEmpty()) {
            return false;
        }
        return !checkList.contains(false);
    }
}
