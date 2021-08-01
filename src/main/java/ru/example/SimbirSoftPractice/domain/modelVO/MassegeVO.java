package ru.example.SimbirSoftPractice.domain.modelVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;

import java.time.LocalDate;
@Data
public class MassegeVO {

    private Long id;

    private Room room;

    private Man man;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;

    private String textMessege;


    public MassegeVO (Messege massege){
        this.id = massege.getId();
        this.date = massege.getDate();
        this.textMessege = massege.getTextMessege();
        this.room = massege.getRoom();
        this.man = massege.getMan();
    }

}
