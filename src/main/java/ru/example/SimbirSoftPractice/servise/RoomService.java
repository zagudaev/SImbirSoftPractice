package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
import ru.example.SimbirSoftPractice.domain.modelVO.RoomVO;

import java.util.List;

public interface RoomService {
    Long save (RoomForm room);
    Long update (RoomForm room);
    void delete (Long id);

    RoomVO findById(Long id);

    List<RoomVO> findAll();
}
