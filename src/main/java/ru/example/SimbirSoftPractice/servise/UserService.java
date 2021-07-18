package ru.example.SimbirSoftPractice.servise;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.example.SimbirSoftPractice.domain.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    long save (User user);
    long update (User user);
    void delete (long id);

    List<User> findAll();



}
