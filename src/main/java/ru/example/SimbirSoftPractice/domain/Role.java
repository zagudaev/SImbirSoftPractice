package ru.example.SimbirSoftPractice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(mappedBy = "role")
    private Collection<User> users;
}
