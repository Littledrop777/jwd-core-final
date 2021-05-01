package com.epam.jwd.core_final.domain;

import java.util.Objects;

/**
 * Expected fields:
 * <p>
 * role {@link Role} - member role
 * rank {@link Rank} - member rank
 * isReadyForNextMissions {@link Boolean} - true by default. Set to false, after first failed mission
 */
public class CrewMember extends AbstractBaseEntity {
    private final Role role;
    private final Rank rank;
    private boolean isReadyForNextMission = true;

    public CrewMember(Role role, String name, Rank rank) {
        super(name);
        this.role = role;
        this.rank = rank;
    }

    public CrewMember(long id, Role role, String name, Rank rank) {
        super(id, name);
        this.role = role;
        this.rank = rank;
    }

    public CrewMember(long id, Role role, String name, Rank rank, boolean isReadyForNextMission) {
        super(id, name);
        this.role = role;
        this.rank = rank;
        this.isReadyForNextMission = isReadyForNextMission;
    }

    public CrewMember withId(long id) {
        return new CrewMember(id, role, getName(), rank);
    }

    public CrewMember readyForNextMission(boolean isReadyForNextMission) {
        return new CrewMember(getId(), role, getName(), rank, isReadyForNextMission);
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isReadyForNextMission() {
        return isReadyForNextMission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewMember that = (CrewMember) o;
        return isReadyForNextMission == that.isReadyForNextMission && role == that.role && rank == that.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, rank, isReadyForNextMission);
    }

    @Override
    public String toString() {
        return "CrewMember " + getName() +
                " role: " + role.name().toLowerCase() +
                ", rank: " + rank.name().toLowerCase();
    }
}
