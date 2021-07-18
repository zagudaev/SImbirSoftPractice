package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.Room;

import java.util.List;

public interface RoomService {
    long save (Room room);
    long update (Room room);
    void delete (long id);

    List<Room> findAll();
}
