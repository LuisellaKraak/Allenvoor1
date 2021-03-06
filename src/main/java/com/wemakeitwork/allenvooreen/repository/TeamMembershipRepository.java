package com.wemakeitwork.allenvooreen.repository;

import com.wemakeitwork.allenvooreen.model.Member;
import com.wemakeitwork.allenvooreen.model.Team;
import com.wemakeitwork.allenvooreen.model.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Integer> {
    Optional<TeamMembership> findByTeam(Team team);
    Optional<TeamMembership> findByMember(Member member);
    TeamMembership findByTeamAndMember(Team team, Member member);
    TeamMembership findByTeamTeamIdAndMemberMemberId(Integer teamId, Integer memberId);
}
