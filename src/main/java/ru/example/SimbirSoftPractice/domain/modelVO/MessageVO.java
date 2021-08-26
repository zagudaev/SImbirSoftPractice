package ru.example.SimbirSoftPractice.domain.modelVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;

import java.time.LocalDate;
@Data
public class MessageVO {

    private Long id;

    private Room room;

    private Man man;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;

    private String textMessege;


    public MessageVO(Message message){
        this.id = message.getId();
        this.date = message.getDate();
        this.textMessege = message.getTextMessege();
        this.room = message.getRoom();
        this.man = message.getMan();
    }

}
