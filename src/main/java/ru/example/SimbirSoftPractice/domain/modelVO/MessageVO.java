package ru.example.SimbirSoftPractice.domain.modelVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Message;

import java.time.LocalDate;
@Data
public class MessageVO {

    private Long id;

    private RoomVO room;

    private ManVO man;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;

    private String textMessege;


    public MessageVO(Message message){
        this.id = message.getId();
        this.date = message.getDate();
        this.textMessege = message.getTextMessage();
        this.room = new RoomVO(message.getRoom());
        this.man = new ManVO(message.getMen());
    }

}
