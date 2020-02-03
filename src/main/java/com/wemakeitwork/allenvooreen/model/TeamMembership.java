package com.wemakeitwork.allenvooreen.model;

import javax.persistence.*;

@Entity
@Table(name = "membership")
public class TeamMembership {
    @Id
    @Column(name = "membership_id")
    private long membershipId;

    @ManyToOne(fetch= FetchType.EAGER)
    private Team team;

    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;

    boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}