package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomVO {

    private Long id;

    private String name;

    private Man creator;

    private boolean privat;

    private List<ManVO> users;

    private List<MessageVO> messeges;

    public RoomVO(Room room){
        this.id = room.getId();
        this.name = room.getName();
        this.creator = room.getCreator();
        this.privat = room.isPrivat();
        if (room.getMen().size() > 0){
            List<ManVO> listVO = new ArrayList<>();
            List<Man> list = room.getMen();
            for (int i = 0; i <room.getMen().size() ; i++) {
                ManVO manVO = new ManVO(list.get(i));
                listVO.add(manVO);
            }
            this.users = listVO;
        }

        if (room.getMessages().size() > 0){
            List<MessageVO> listVO = new ArrayList<>();
            List<Message> list = room.getMessages();
            for (int i = 0; i <room.getMessages().size() ; i++) {
                MessageVO messageVO = new MessageVO(list.get(i));
                listVO.add(messageVO);
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
