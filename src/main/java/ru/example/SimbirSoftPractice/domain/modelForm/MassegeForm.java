package ru.example.SimbirSoftPractice.domain.modelForm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.UserDao;


import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Optional;

@Data
public class MassegeForm {
    private Long id;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long roomId;
    @NotBlank
    private String textMassege;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm a z")
    private LocalDate date;


    public Massege toMassege(UserDao userDao, RoomDao roomDao){
        Massege massege = new Massege();
        massege = update(massege,userDao,roomDao);
        return massege;
    }
    public Massege update(Massege massege,UserDao userDao, RoomDao roomDao){
        massege.setDate(date);
        massege.setTextMassege(textMassege);
        Optional<User> user =userDao.findById(userId);
        massege.setUser(user.get());      //????????
        Optional<Room> room = roomDao.findById(roomId);
        massege.setRoom(room.get());
        return massege;
    }

}
