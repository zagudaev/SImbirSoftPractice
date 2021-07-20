package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public long save(UserForm user) {
        return 0;
    }

    @Override
    public long update(UserForm user) {
        return 0;
    }

    @Override
    public void delete(long id) {}

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
