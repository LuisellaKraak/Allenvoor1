package com.wemakeitwork.allenvooreen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonPropertyOrder(value = {"id","name"}, alphabetic = true)
// Ignoring 'hibernateLazyInitializer' & 'handler' is needed to prevent infinite recursion
// when calling the ObjectMapper to create a JSON
@JsonIgnoreProperties({ "allMembersInThisTeamSet", "eventList", "hibernateLazyInitializer", "handler" })
public class Team {
    public Team() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int teamId = 0;

    @JsonProperty("name")
    private String teamName;

    @ManyToMany(mappedBy = "teamName")
    private Set<Member> allMembersInThisTeamSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "team")
    List<Event> eventList = new ArrayList<>();

    public Set<Member> getAllMembersInThisTeamSet() {
        return allMembersInThisTeamSet;
    }

    public void setAllMembersInThisTeamSet(Set<Member> allMembersInThisTeamSet) {
        this.allMembersInThisTeamSet = allMembersInThisTeamSet;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String setTeamName(String teamName) {
        if (teamName != null && teamName.isEmpty()) {
            return null;
        } else {
            return this.teamName = teamName;
        }
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(Event event) {
        this.eventList.add(event);
    }

    public void addTeamMember(Member member){
        allMembersInThisTeamSet.add(member);
    }

    public void removeTeamMember(Member member){
        allMembersInThisTeamSet.remove(member);
    }
}
