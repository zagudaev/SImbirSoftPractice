package ru.example.SimbirSoftPractice.domain.modelForm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.ManDao;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class MessegeForm {
    private Long id;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long roomId;
    @NotBlank
    private String textMassege;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;


    public Messege toMessege(ManDao manDao, RoomDao roomDao){
        Messege messege = new Messege();
        messege = update(messege, manDao,roomDao);
        return messege;
    }
    public Messege update(Messege messege, ManDao manDao, RoomDao roomDao){
        messege.setDate(date);
        messege.setTextMessege(textMassege);
        Optional<Man> user = manDao.findById(userId);
        messege.setMan(user.get());      //????????
        Optional<Room> room = roomDao.findById(roomId);
        messege.setRoom(room.get());
        return messege;
    }

}
