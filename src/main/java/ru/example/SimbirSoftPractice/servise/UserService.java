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



}
