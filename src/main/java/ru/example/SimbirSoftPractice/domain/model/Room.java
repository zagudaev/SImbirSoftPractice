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
    private Men creator;

    private boolean privat;


    @ManyToMany
    private List<Men> men;

    @OneToMany(mappedBy = "room")
    private List<Message> messages;

}
