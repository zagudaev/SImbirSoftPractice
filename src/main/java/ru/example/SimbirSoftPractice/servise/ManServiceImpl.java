package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.SimbirSoftPractice.exception.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.Man;
import ru.example.SimbirSoftPractice.domain.modelForm.ManForm;
import ru.example.SimbirSoftPractice.domain.modelVO.ManVO;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.RoleDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.ManDao;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ManServiceImpl implements ManService {
    private final MessegeDao messegeDao;
    private final ManDao manDao;
    private final RoomDao roomDao;
    private  final RoleDao roleDao;
    @Override
    @Transactional
    public Long save(ManForm manForm) {
        if (manDao.findByLogin(manForm.getLogin()).orElse(null) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите другое имя пользователя, данный login уже заня  : " + manForm.getLogin());
        }
        Man man = manForm.toUser();
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'USER' FOUND"));
        man.setRole(role);
        man.setPassword(bCryptPasswordEncoder().encode(man.getPassword()));
        return manDao.save(man).getId();
    }
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public Long update(ManForm manForm) {
        Man man = manDao.findById(manForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + manForm.getId()));

        man = manForm.update(man);
        return manDao.save(man).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR')")
    public void delete(Long id) {
        Man man = manDao.findById(id).orElseThrow(() ->
            new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + id));
        manDao.deleteById(id);}

    @Override
    @Transactional(readOnly = true)
    public List<ManVO> findAll() {
        return manDao.findAll()
                .stream()
                .map(ManVO::new)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public Man findByLogin(String login) {
        Man man =  manDao.findByLogin(login)
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "User NOT FOUND"));
        return man;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') ")
    public void ban(ManForm manForm) {
        Man man = manDao.findById(manForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + manForm.getId()));
        man.setBan(true);
        manDao.save(man);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') ")
    public void unBan(ManForm manForm) {
        Man man = manDao.findById(manForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + manForm.getId()));
        man.setBan(false);
        manDao.save(man);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public void addModerator(ManForm manForm) {
        Man man = manDao.findById(manForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + manForm.getId()));
        Role role = roleDao.findByName("ROLE_MODERATOR")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'ROLE_MODERATOR' FOUND"));
        man.setRole(role);
        manDao.save(man);

    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public void deleteModerator(ManForm manForm) {
        Man man = manDao.findById(manForm.getId()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + manForm.getId()));
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'ROLE_USER' FOUND"));
        man.setRole(role);
        manDao.save(man);
    }

    @Override
    public void commandRename(ManForm manForm, String newUsername) {
        Man man = manDao.findByLogin(manForm.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + manForm.getLogin()));

        man.setUsername(newUsername);
        manDao.save(man);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Man man = manDao.findByLogin(username)
                .orElse(null);
        if (man == null) return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
                getGrantedAuthorities("user"));
        return new org.springframework.security.core.userdetails.User(man.getLogin(), man.getPassword(), true, true, true, true,
                getGrantedAuthorities(man.getRole().getName()));
    }

    private List<? extends GrantedAuthority> getGrantedAuthorities(String privilege) {
        return Arrays.asList(new SimpleGrantedAuthority(privilege));
    }
}
