package ru.example.SimbirSoftPractice.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "id_creator")
    private User creator;

    private boolean privat;


    @ManyToMany
    private List<User> users;

    @OneToMany(mappedBy = "room")
    private List<Messege> messeges;

}
