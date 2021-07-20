package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;

import java.util.List;

public interface RoomService {
    long save (RoomForm room);
    long update (RoomForm room);
    void delete (long id);

    List<Room> findAll();
}
