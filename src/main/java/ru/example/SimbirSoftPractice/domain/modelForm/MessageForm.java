package ru.example.SimbirSoftPractice.domain.modelForm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.ManDao;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class MessageForm {
    private Long id;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long roomId;
    @NotBlank
    private String textMassege;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;


    public Message toMessege(ManDao manDao, RoomDao roomDao){
        Message message = new Message();
        message = update(message, manDao,roomDao);
        return message;
    }
    public Message update(Message message, ManDao manDao, RoomDao roomDao){
        message.setDate(date);
        message.setTextMessege(textMassege);
        Optional<Man> user = manDao.findById(userId);
        message.setMan(user.get());      //????????
        Optional<Room> room = roomDao.findById(roomId);
        message.setRoom(room.get());
        return message;
    }

}
