package com.wemakeitwork.allenvooreen.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "members")
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId = 0;

    private String memberName;

    private String password;

    private String rol;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "team_membername", joinColumns = @JoinColumn(name = "membername_member_id"), inverseJoinColumns = @JoinColumn(name = "team_team_id"))
    private Set<Team> allTeamsOfMemberSet = new HashSet<>();

    public Set<Team> getAllTeamsOfMemberSet() {
        return allTeamsOfMemberSet;
    }

    public void setAllTeamsOfMemberSet(Set<Team> allTeamsOfMemberSet) {
        this.allTeamsOfMemberSet = allTeamsOfMemberSet;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getMemberName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        if (password != null && password.isEmpty()) {
            return null;
        } else {
            return this.password = password;
        }
    }

    public String getMemberName() {
        return memberName;
    }

    public String setMemberName(String memberName) {
        if (memberName != null && memberName.isEmpty()) {
            return null;
        } else {
            return this.memberName = memberName;
        }
    }

    public void removeTeamFromMember(Team team){
        allTeamsOfMemberSet.remove(team);
    }
}