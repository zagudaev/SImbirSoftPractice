package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
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
    public Long save(RoomForm roomForm) {
        if (roomDao.findById(roomForm.getId()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка создания комнты по id  : " + roomForm.getId() );
        }
        Room room = roomForm.toRoom(userDao, messegeDao);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
    public Long update(RoomForm roomForm) {
        Room room= roomDao.findById(roomForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomForm.getId()));

        room = roomForm.update(room,userDao, messegeDao);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
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
}
