package com.km.socserv.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "t_users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String role;
    private String phoneNumber;


    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<User> roles = new HashSet<>();
        roles.add(this);
        return roles;
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
}
