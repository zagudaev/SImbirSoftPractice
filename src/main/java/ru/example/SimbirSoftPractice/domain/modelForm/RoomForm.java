package ru.example.SimbirSoftPractice.domain.modelForm;

import lombok.Data;
import lombok.Getter;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.UserDao;

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


    private List<UserForm> users;

    private List<MessegeForm> massageForms;


    public Room toRoom(UserDao userDao, MessegeDao massegeDao){
        Room room = new Room();
        room = update(room,userDao,massegeDao);
        return room;
    }
    public Room update(Room room,UserDao userDao, MessegeDao massegeDao){
        room.setName(name);
        room.setPrivat(privat);
        Optional<User> creator = userDao.findById(creatorId);
        room.setCreator(creator.get());
        List<User> users = this.users
                .stream()
                .filter(UserForm -> UserForm.getId() == null)
                .map(p -> {
                    User user = new User();
                    user.setId(p.getId());
                    user.setLogin(p.getLogin());
                    user.setPassword(p.getPassword());
                    user.setUsername(p.getUsername());
                    user.setBan(p.isBan());
                    userDao.save(user);
                    return user;
                }).collect(Collectors.toList());

        room.setUsers(users);

        List<Messege> masseges = this.massageForms
                .stream()
                .filter(MassegeForm -> MassegeForm.getId() == null)
                .map(p -> {
                    Messege massege = new Messege();
                    massege.setRoom(room);
                    massege.setId(p.getId());
                    massege.setDate(p.getDate());
                    Optional<User> user = userDao.findById(p.getUserId());
                    massege.setUser(user.get());
                    massegeDao.save(massege);
                    return massege;
                }).collect(Collectors.toList());
        room.setMesseges(masseges);

        return room;
    }
}
