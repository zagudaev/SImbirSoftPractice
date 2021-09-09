package ru.example.SimbirSoftPractice.domain.modelDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data

public class RoomDTO {


    private Long creator;
    @NotBlank
    private String name;

    private boolean privat;


    private List<MenDTO> men;

    private List<MessageDTO> messages;




}
