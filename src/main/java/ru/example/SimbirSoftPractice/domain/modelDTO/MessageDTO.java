package ru.example.SimbirSoftPractice.domain.modelDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class MessageDTO {

    private Long id;

    @NotBlank
    private Long men;
    @NotBlank
    private Long room;

    private String textMessage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDate date;




}
