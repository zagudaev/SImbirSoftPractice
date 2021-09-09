package ru.example.SimbirSoftPractice.domain.modelDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class MenDTO {

    @NotBlank
    private String login;

    private String password;

    private String username;

    private boolean ban;



}
