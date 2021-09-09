package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;
import ru.example.SimbirSoftPractice.domain.modelDTO.RoomDTO;

import java.util.List;

public interface RoomService {
    Long save (RoomDTO roomDTO);
    Long update (RoomDTO roomDTO);
    void delete (Long id);
    void commandUpdate (Room room);

    RoomDTO findById(Long id);

    List<RoomDTO> findAll();

    void addUser(RoomDTO roomDTO, MenDTO menDTO);
    void deleteUser(RoomDTO roomDTO, MenDTO menDTO);
    public void deleteUserComand(Room room, Men men);
}
