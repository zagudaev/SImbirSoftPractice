package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Data
public class RoomVO {

    private Long id;

    private String name;

    private User creator;

    private boolean privat;

    private List<User> users;

    private List<Massege> masseges;

    public RoomVO(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.creator = room.getCreator();
        this.privat = room.isPrivat();
        //TODO НЕПОЛУЧАЕТСЯ ДОБАВТЬ ЛИСТЫ
        /*
        if (room.getUsers().size() > 0) {
            List<User> list = new ArrayList<>();
            for (User user : room.getUsers()) {
                UserVO userVO = new UserVO(user);
                list.add(userVO);
            }
            this.users = list;
        }
        if (room.getMasseges().size() > 0) {
            List<Massege> list = new ArrayList<>();
            for (Massege massege : room.getMasseges()) {
                MassegeVO massegeVO = new MassegeVO(massege);
                list.add(massegeVO);
            }
            this.masseges = list;

        if (room.getUsers().size() > 0)
            this.users = room.getUsers()
                    .stream()
                    .map(RoomVO::new)
                    .collect(Collectors.toList());
    }*/
    }
}
