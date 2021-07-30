package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.SimbirSoftPractice.util.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.domain.modelVO.UserVO;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.RoleDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.UserDao;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final MessegeDao messegeDao;
    private final UserDao userDao;
    private final RoomDao roomDao;
    private  final RoleDao roleDao;
    @Override
    @Transactional
    public Long save(UserForm userForm) {
        if (userDao.findByLogin(userForm.getLogin()).orElse(null) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите другое имя пользователя, данный login уже заня  : " + userForm.getLogin());
        }
        User user = userForm.toUser();
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'USER' FOUND"));
        user.setRole(role);
        user.setPassword(bCryptPasswordEncoder().encode(user.getPassword()));
        return userDao.save(user).getId();
    }
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public Long update(UserForm userForm) {
        User user= userDao.findById(userForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + userForm.getId()));

        user = userForm.update(user);
        return userDao.save(user).getId();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user= userDao.findById(id).orElseThrow(() ->
            new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + id));
        userDao.deleteById(id);}

    @Override
    @Transactional(readOnly = true)
    public List<UserVO> findAll() {
        return userDao.findAll()
                .stream()
                .map(UserVO::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        User user =  userDao.findByLogin(login)
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "User NOT FOUND"));
        return user;
    }

    @Override
    @Transactional
    public void ban(UserForm userForm) {
        User user= userDao.findById(userForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + userForm.getId()));
        user.setBan(true);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void unBan(UserForm userForm) {
        User user= userDao.findById(userForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + userForm.getId()));
        user.setBan(false);
        userDao.save(user);
    }

    @Override
    @Transactional
    public void addModerator(UserForm userForm) {
        User user= userDao.findById(userForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + userForm.getId()));
        Role role = roleDao.findByName("ROLE_MODERATOR")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'USER' FOUND"));
        user.setRole(role);
        userDao.save(user);

    }

    @Override
    @Transactional
    public void deleteModerator(UserForm userForm) {
        User user= userDao.findById(userForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + userForm.getId()));
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'USER' FOUND"));
        user.setRole(role);
        userDao.save(user);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByLogin(username)
                .orElse(null);
        if (user == null) return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
                getGrantedAuthorities("user"));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), true, true, true, true,
                getGrantedAuthorities(user.getRole().getName()));
    }

    private List<? extends GrantedAuthority> getGrantedAuthorities(String privilege) {
        return Arrays.asList(new SimpleGrantedAuthority(privilege));
    }
}
