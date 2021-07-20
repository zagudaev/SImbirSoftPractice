package ru.example.SimbirSoftPractice.servise;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;

import java.util.List;

public interface UserService extends UserDetailsService {
    long save (UserForm user);
    long update (UserForm user);
    void delete (long id);

    List<User> findAll();



}
