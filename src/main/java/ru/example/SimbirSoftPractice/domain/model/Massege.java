package ru.example.SimbirSoftPractice.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Massege {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn
    private Room room;

    @ManyToOne
    @JoinColumn
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;

    @Column(length = 500)
    private String textMassege;
}
