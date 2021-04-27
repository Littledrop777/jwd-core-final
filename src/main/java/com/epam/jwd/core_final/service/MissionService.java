package com.epam.jwd.core_final.service;

import com.epam.jwd.core_final.criteria.Criteria;
import com.epam.jwd.core_final.criteria.FlightMissionCriteria;
import com.epam.jwd.core_final.domain.FlightMission;

import java.util.List;
import java.util.Optional;

public interface MissionService {

    List<FlightMission> findAllMissions();

    List<FlightMission> findAllMissionsByCriteria(FlightMissionCriteria criteria);

    Optional<FlightMission> findMissionByCriteria(FlightMissionCriteria criteria);

    FlightMission updateMission(FlightMission flightMission);

    FlightMission createMission(FlightMission flightMission);
}
