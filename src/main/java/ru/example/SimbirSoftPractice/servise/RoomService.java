package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
import ru.example.SimbirSoftPractice.domain.modelForm.ManForm;
import ru.example.SimbirSoftPractice.domain.modelVO.RoomVO;

import java.util.List;

public interface RoomService {
    Long save (RoomForm roomForm);
    Long update (RoomForm roomForm);
    void delete (Long id);

    RoomVO findById(Long id);

    List<RoomVO> findAll();

    void addUser(RoomForm roomForm, ManForm manForm);
    void deleteUser(RoomForm roomForm, ManForm manForm);
}
