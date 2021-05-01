package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;

/**
 * Should be a builder for {@link com.epam.jwd.core_final.domain.CrewMember} fields
 */
public class CrewMemberCriteria extends Criteria<CrewMember> {
    private final Role role;
    private final Rank rank;

    private CrewMemberCriteria(long id, String name, Role role, Rank rank) {
        super(id, name);
        this.role = role;
        this.rank = rank;
    }

    public Role getRole() {
        return role;
    }

    public Rank getRank() {
        return rank;
    }

    public static CriteriaBuilder create() {
        return new CriteriaBuilder();
    }

    public static class CriteriaBuilder {
        private long id;
        private String name;
        private Role role;
        private Rank rank;

        public CriteriaBuilder() {
        }

        public CriteriaBuilder whereId(long id) {
            this.id = id;
            return this;
        }

        public CriteriaBuilder whereName(String name) {
            this.name = name;
            return this;
        }

        public CriteriaBuilder whereRole(Role role) {
            this.role = role;
            return this;
        }

        public CriteriaBuilder whereRank(Rank rank) {
            this.rank = rank;
            return this;
        }

        public CrewMemberCriteria build() {
            return new CrewMemberCriteria(id, name, role, rank);
        }
    }
}
