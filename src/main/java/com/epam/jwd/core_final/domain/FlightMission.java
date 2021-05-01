package com.epam.jwd.core_final.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Expected fields:
 * <p>
 * missions name {@link String}
 * start date {@link java.time.LocalDate}
 * end date {@link java.time.LocalDate}
 * distance {@link Long} - missions distance
 * assignedSpaceShift {@link Spaceship} - not defined by default
 * assignedCrew {@link java.util.List<CrewMember>} - list of missions members based on ship capacity - not defined by default
 * missionResult {@link MissionResult}
 * from {@link Planet}
 * to {@link Planet}
 */
public class FlightMission extends AbstractBaseEntity {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final long distance;
    private final Spaceship assignedSpaceShip;
    private final List<CrewMember> assignedCrew;
    private final MissionResult missionResult;
    private final Planet from;
    private final Planet to;


    private FlightMission(long id, String name, LocalDate startDate, LocalDate endDate, long distance, Spaceship assignedSpaceShip, List<CrewMember> assignedCrew, MissionResult missionResult, Planet from, Planet to) {
        super(id, name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.assignedSpaceShip = assignedSpaceShip;
        this.assignedCrew = assignedCrew;
        this.missionResult = missionResult;
        this.from = from;
        this.to = to;
    }


    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getDistance() {
        return distance;
    }

    public Spaceship getAssignedSpaceShip() {
        return assignedSpaceShip;
    }

    public List<CrewMember> getAssignedCrew() {
        return assignedCrew;
    }

    public MissionResult getMissionResult() {
        return missionResult;
    }

    public Planet getFrom() {
        return from;
    }

    public Planet getTo() {
        return to;
    }

    @Override
    public String toString() {
        return infoToString() + crewToString();
    }

    private String crewToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------Crew members-----------------------").append('\n')
                .append(String.format("%-20s %-20s %-15s\n", "Role", "Name", "Rank"))
                .append("---------------------------------------------------------\n");
        for (CrewMember crewMember : assignedCrew) {
            sb.append(String.format("%-20s %-20s %-15s", crewMember.getRole().getName(), crewMember.getName(), crewMember.getRank().getName()))
                    .append("\n");
        }
        return sb.toString();
    }

    private String infoToString() {
        String missionName = String.format("%-18s", "name");
        String start = String.format("%-18s", "start date");
        String end = String.format("%-18s", "end date");
        String dist = String.format("%-18s", "distance");
        String spaceShip = String.format("%-18s", "assigned ship");
        String crew = String.format("%-18s", "assigned crew");
        String result = String.format("%-18s", "result of mission");
        String planetFrom = String.format("%-18s", "from planet");
        String planetTo = String.format("%-18s", "to planet");

        return "Flight mission\n" + "╔══════════════════╦══════════════════╗\n" +
                "║" + missionName + "║" + String.format("%-18s", getName()) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + start + "║" + String.format("%-18s", startDate) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + end + "║" + String.format("%-18s", endDate) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + dist + "║" + String.format("%-18d", distance) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + spaceShip + "║" + String.format("%-18s", assignedSpaceShip.getName()) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + result + "║" + String.format("%-18s", missionResult.name()) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + planetFrom + "║" + String.format("%-18s", from.getName()) + "║\n" +
                "╠══════════════════╬══════════════════╣\n" +
                "║" + planetTo + "║" + String.format("%-18s", to.getName()) + "║\n" +
                "╚══════════════════╩══════════════════╝\n\n";
    }

    public static FlightMissionBuilder construct() {
        return new FlightMissionBuilder();
    }


    public static class FlightMissionBuilder {
        private long id;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private long distance;
        private Spaceship assignedSpaceShip;
        private List<CrewMember> assignedCrew;
        private MissionResult missionResult;
        private Planet from;
        private Planet to;

        public FlightMissionBuilder() {
        }

        public FlightMissionBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public FlightMissionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public FlightMissionBuilder setStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public FlightMissionBuilder setEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public FlightMissionBuilder setDistance(long distance) {
            this.distance = distance;
            return this;
        }

        public FlightMissionBuilder setAssignedSpaceShip(Spaceship assignedSpaceShip) {
            this.assignedSpaceShip = assignedSpaceShip;
            return this;
        }

        public FlightMissionBuilder setAssignedCrew(List<CrewMember> assignedCrew) {
            this.assignedCrew = assignedCrew;
            return this;
        }

        public FlightMissionBuilder setMissionResult(MissionResult missionResult) {
            this.missionResult = missionResult;
            return this;
        }

        public FlightMissionBuilder setFrom(Planet from) {
            this.from = from;
            return this;
        }

        public FlightMissionBuilder setTo(Planet to) {
            this.to = to;
            return this;
        }

        public FlightMission build() {
            return new FlightMission(id, name, startDate, endDate, distance, assignedSpaceShip, assignedCrew, missionResult, from, to);
        }
    }
}
