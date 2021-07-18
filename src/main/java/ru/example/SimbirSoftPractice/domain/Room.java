package ru.example.SimbirSoftPractice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table
public class Room {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn
    @Column(name = "id_creator")
    private User creator;

    private boolean privat;

    @ManyToMany(mappedBy = "room" )
    private Collection<User> users;

    @OneToMany(mappedBy = "room")
    private List<Massege> masseges;

}
