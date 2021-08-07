package ru.example.SimbirSoftPractice.domain.modelVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;

import java.time.LocalDate;
@Data
public class MessegeVO {

    private Long id;

    private Room room;

    private Man man;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;

    private String textMessege;


    public MessegeVO(Messege messege){
        this.id = messege.getId();
        this.date = messege.getDate();
        this.textMessege = messege.getTextMessege();
        this.room = messege.getRoom();
        this.man = messege.getMan();
    }

}
