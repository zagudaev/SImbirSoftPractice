package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.util.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
import ru.example.SimbirSoftPractice.domain.modelVO.RoomVO;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.UserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;
    private final UserDao userDao;
    private final MessegeDao messegeDao;

    @Override
    @Transactional
    @PreAuthorize("#roomServiceImpl.findById(roomForm.id).creator.ban == false ") //TODO в spel-выражения я не уверен
    public Long save(RoomForm roomForm) {
        if (roomDao.findById(roomForm.getId()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка создания комнты по id  : " + roomForm.getId() );
        }
        Room room = roomForm.toRoom(userDao, messegeDao);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomForm.creatorId== authentication.principal.id")
    public Long update(RoomForm roomForm) {
        Room room= roomDao.findById(roomForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomForm.getId()));

        room = roomForm.update(room,userDao, messegeDao);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #roomServiceImpl.findById(roomForm.id).creator.id == authentication.principal.id") //TODO в spel-выражения я не уверен
    public void delete(Long id) {
        Room room= roomDao.findById(id).orElseThrow(() ->
            new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + id));
        roomDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RoomVO findById(Long id) {
        Room room  = roomDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + id));
        RoomVO roomVO = new RoomVO(room);
        return roomVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomVO> findAll() {
        return roomDao.findAll()
                .stream()
                .map(RoomVO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #roomForm.creatorId== authentication.principal.id") //TODO в spel-выражения я не уверен
    public void addUser(RoomForm roomForm, UserForm userForm) {
        Room room= roomDao.findById(roomForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomForm.getId()));
        User user = userForm.toUser();
        List<User> list = room.getUsers();
        list.add(user);
        room.setUsers(list);
        roomDao.save(room);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomForm.creatorId== authentication.principal.id") //TODO в spel-выражения я не уверен
    public void deleteUser(RoomForm roomForm, UserForm userForm) {
        Room room= roomDao.findById(roomForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomForm.getId()));
        User user = userForm.toUser();
        List<User> list = room.getUsers();
        list.remove(user);
        room.setUsers(list);
        roomDao.save(room);
    }
}
