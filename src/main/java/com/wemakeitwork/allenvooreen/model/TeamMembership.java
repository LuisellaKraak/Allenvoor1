package com.wemakeitwork.allenvooreen.model;

import javax.persistence.*;

@Entity
@Table(name = "membership")
public class TeamMembership {
    @Id
    @Column(name = "membership_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer membershipId;

    @ManyToOne(fetch= FetchType.EAGER)
    private Team team;

    @ManyToOne(fetch= FetchType.EAGER)
    private Member member;

    boolean isAdmin;

    public TeamMembership(Team team, Member member, boolean isAdmin) {
        this.team = team;
        this.member = member;
        this.isAdmin = isAdmin;
    }

    public TeamMembership() {
    }

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

    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }
}