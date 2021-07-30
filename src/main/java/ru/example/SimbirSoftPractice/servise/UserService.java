package ru.example.SimbirSoftPractice.servise;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.domain.modelVO.UserVO;

import java.util.List;

public interface UserService extends UserDetailsService {
    Long save (UserForm user);
    Long update (UserForm user);
    void delete (Long id);

    List<UserVO> findAll();
    User findByLogin(String login);

    void ban(UserForm userForm);
    void unBan (UserForm userForm);

    void addModerator(UserForm userForm);
    void deleteModerator(UserForm userForm);


}
