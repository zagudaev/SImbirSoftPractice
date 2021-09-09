package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;
import ru.example.SimbirSoftPractice.exception.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelDTO.RoomDTO;
import ru.example.SimbirSoftPractice.mappers.RoomMapper;
import ru.example.SimbirSoftPractice.repository.MessageDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.MenDao;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;
    private final MenDao menDao;
    private final MessageDao messageDao;
    //private final RoomMapper roomMapper;

    @Override
    @Transactional
    //@PreAuthorize("#manDao.findById(roomForm.creatorId).get().ban == false") //TODO в spel-выражения я не уверен
    public Long save(RoomDTO roomDTO) {
        if (roomDao.findByName(roomDTO.getName()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка создания комнты по Name  : " + roomDTO.getName() );
        }
        Room room = new Room();
        room = RoomMapper.INSTANCE.toRoom(roomDTO);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomDTO.creator== authentication.principal.id")
    public Long update(RoomDTO roomDTO) {
        Room room= roomDao.findByName(roomDTO.getName()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomDTO.getName()));

        room = RoomMapper.INSTANCE.updateRoom(roomDTO,room);
        return roomDao.save(room).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomForm.creatorId== authentication.principal.id")
    public void commandUpdate(Room room) {
         roomDao.save(room);
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
    public RoomDTO findById(Long id) {
        Room room  = roomDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + id));

        return RoomMapper.INSTANCE.toRoomDTO(room);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomDTO> findAll() {
        List<Room> roomList = roomDao.findAll();
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room : roomList) {
            roomDTOList.add(RoomMapper.INSTANCE.toRoomDTO(room));
        }

        return roomDTOList;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #roomDTO.creator== authentication.principal.id") //TODO в spel-выражения я не уверен
    public void addUser(RoomDTO roomDTO, MenDTO menDTO) {
        Room room= roomDao.findByName(roomDTO.getName()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomDTO.getName()));
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + menDTO.getLogin()));
        List<Men> list = room.getMen();
        list.add(men);
        room.setMen(list);
        roomDao.save(room);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomDTO.creator == authentication.principal.id OR #menDTO.id == authentication.principal.id") //TODO в spel-выражения я не уверен
    public void deleteUser(RoomDTO roomDTO, MenDTO menDTO) {
        Room room= roomDao.findByName(roomDTO.getName()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найдена комната с ID = " + roomDTO.getName()));
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + menDTO.getLogin()));
        List<Men> list = room.getMen();
        list.remove(men);
        room.setMen(list);
        roomDao.save(room);
    }
    public void deleteUserComand(Room room, Men men) {
        List<Men> list = room.getMen();
        list.remove(men);
        room.setMen(list);
        roomDao.save(room);
    }
}
