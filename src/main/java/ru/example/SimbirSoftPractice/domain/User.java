package ru.example.SimbirSoftPractice.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    private boolean ban;

    @ManyToOne
    @JoinColumn
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Massege> masseges;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
