package ru.example.SimbirSoftPractice.domain.modelForm;

import lombok.Data;
import lombok.Getter;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.Man;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.ManDao;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Data
@Getter
public class RoomForm {
    private Long id;
    @NotBlank
    private Long creatorId;
    @NotBlank
    private String name;
    @NotBlank
    private boolean privat;


    private List<ManForm> users;

    private List<MessegeForm> massageForms;


    public Room toRoom(ManDao manDao, MessegeDao massegeDao){
        Room room = new Room();
        room = update(room, manDao,massegeDao);
        return room;
    }
    public Room update(Room room, ManDao manDao, MessegeDao massegeDao){
        room.setName(name);
        room.setPrivat(privat);
        Optional<Man> creator = manDao.findById(creatorId);
        room.setCreator(creator.get());
        List<Man> men = this.users
                .stream()
                .filter(UserForm -> UserForm.getId() == null)
                .map(p -> {
                    Man man = new Man();
                    man.setId(p.getId());
                    man.setLogin(p.getLogin());
                    man.setPassword(p.getPassword());
                    man.setUsername(p.getUsername());
                    man.setBan(p.isBan());
                    manDao.save(man);
                    return man;
                }).collect(Collectors.toList());

        room.setMen(men);

        List<Messege> masseges = this.massageForms
                .stream()
                .filter(MassegeForm -> MassegeForm.getId() == null)
                .map(p -> {
                    Messege massege = new Messege();
                    massege.setRoom(room);
                    massege.setId(p.getId());
                    massege.setDate(p.getDate());
                    Optional<Man> user = manDao.findById(p.getUserId());
                    massege.setMan(user.get());
                    massegeDao.save(massege);
                    return massege;
                }).collect(Collectors.toList());
        room.setMesseges(masseges);

        return room;
    }
}
