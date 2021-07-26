package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomVO {

    private Long id;

    private String name;

    private User creator;

    private boolean privat;

    private List<UserVO> users;

    private List<MassegeVO> messeges;

    public RoomVO(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.creator = room.getCreator();
        this.privat = room.isPrivat();
        if (room.getUsers().size() > 0){
            List<UserVO> listVO = new ArrayList<>();
            List<User> list = room.getUsers();
            for (int i = 0; i <room.getUsers().size() ; i++) {
                UserVO userVO = new UserVO(list.get(i));
                listVO.add(userVO);
            }
            this.users = listVO;
        }

        if (room.getMesseges().size() > 0){
            List<MassegeVO> listVO = new ArrayList<>();
            List<Messege> list = room.getMesseges();
            for (int i = 0; i <room.getMesseges().size() ; i++) {
                MassegeVO massegeVO = new MassegeVO(list.get(i));
                listVO.add(massegeVO);
            }
            this.messeges = listVO;
        }
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
