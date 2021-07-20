package ru.example.SimbirSoftPractice.domain.modelForm;

import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.repository.MassegeDao;
import ru.example.SimbirSoftPractice.repository.UserDao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomForm {
    private Long id;
    @NotBlank
    private Long creatorId;
    @NotBlank
    private String name;
    @NotBlank
    private boolean privat;


    private List<UserForm> users;

    private List<MassegeForm> masseges;


    public Room toRoom(UserDao userDao, MassegeDao massegeDao){
        Room room = new Room();
        room = update(room,userDao,massegeDao);
        return room;
    }
    public Room update(Room room,UserDao userDao, MassegeDao massegeDao){
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

        List<Massege> masseges = this.masseges
                .stream()
                .filter(MassegeForm -> MassegeForm.getId() == null)
                .map(p -> {
                    Massege massege = new Massege();
                    massege.setRoom(room);
                    massege.setId(p.getId());
                    massege.setDate(p.getDate());
                    Optional<User> user = userDao.findById(p.getUserId());
                    massege.setUser(user.get());
                    massegeDao.save(massege);
                    return massege;
                }).collect(Collectors.toList());
        room.setMasseges(masseges);

        return room;
    }
}
