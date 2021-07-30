package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.domain.modelVO.RoomVO;

import java.util.List;

public interface RoomService {
    Long savePublicRoom (RoomForm roomForm);
    Long update (RoomForm roomForm);
    void delete (Long id);

    RoomVO findById(Long id);

    List<RoomVO> findAll();

    void addUser(RoomForm roomForm, UserForm userForm);
    void deleteUser(RoomForm roomForm, UserForm userForm);
}
