package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.SimbirSoftPractice.domain.Room;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    @Override
    public long save(Room room) {
        return 0;
    }

    @Override
    public long update(Room room) {
        return 0;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Room> findAll() {
        return null;
    }
}
