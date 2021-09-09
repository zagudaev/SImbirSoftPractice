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
import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;
import ru.example.SimbirSoftPractice.exception.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.mappers.MenMapper;
import ru.example.SimbirSoftPractice.mappers.MenMapperImpl;
import ru.example.SimbirSoftPractice.repository.RoleDao;
import ru.example.SimbirSoftPractice.repository.MenDao;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class MenServiceImpl implements MenService {
    private final MenDao menDao;
    private  final RoleDao roleDao;
    //private final MenMapper menMapper;


    @Override
    @Transactional
    public Long save(MenDTO menDTO) {
        if (menDao.findByLogin(menDTO.getLogin()).orElse(null) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Введите другое имя пользователя, данный login уже заня  : " + menDTO.getLogin());
        }
        Men men = new Men();
        men = MenMapper.INSTANCE.toMen(menDTO);
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'USER' FOUND"));
        men.setRole(role);
        men.setPassword(bCryptPasswordEncoder().encode(men.getPassword()));
        return menDao.save(men).getId();
    }
    private BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public Long update(MenDTO menDTO) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + menDTO.getLogin()));

        men = MenMapper.INSTANCE.updateMen(men,menDTO);
        return menDao.save(men).getId();
    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR')")
    public void delete(Long id) {
        Men men = menDao.findById(id).orElseThrow(() ->
            new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с ID = " + id));
        menDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenDTO> findAll() {
        List<Men> menList = menDao.findAll();
        List<MenDTO> menDTOList = new ArrayList<>();
        for (int i = 0; i < menList.size(); i++) {
            menDTOList.add(MenMapper.INSTANCE.toMenDTO( menList.get(i)));
        }

        return menDTOList;


    }


    @Override
    @Transactional(readOnly = true)
    public Men findByLogin(String login) {
        Men men =  menDao.findByLogin(login)
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "User NOT FOUND"));
        return men;
    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') ")
    public void ban(MenDTO menDTO) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + menDTO.getLogin()));
        men.setBan(true);
        menDao.save(men);
    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') ")
    public void unBan(MenDTO menDTO) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + menDTO.getLogin()));
        men.setBan(false);
        menDao.save(men);
    }

    @Override
    @Transactional
   // @PreAuthorize("hasRole('ROLE_ADMIN') ")
    public void addModerator(MenDTO menDTO) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + menDTO.getLogin()));
        Role role = roleDao.findByName("ROLE_MODERATOR")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'ROLE_MODERATOR' FOUND"));
        men.setRole(role);
        menDao.save(men);

    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('ROLE_ADMIN') ")
    public void deleteModerator(MenDTO menDTO) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + menDTO.getLogin()));
        Role role = roleDao.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseException(HttpStatus.BAD_REQUEST, "Role NOT 'ROLE_USER' FOUND"));
        men.setRole(role);
        menDao.save(men);
    }

    @Override
    public void commandRename(MenDTO menDTO, String newUsername) {
        Men men = menDao.findByLogin(menDTO.getLogin()).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден пользователь с Login = " + menDTO.getLogin()));

        men.setUsername(newUsername);
        menDao.save(men);
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Men men = menDao.findByLogin(username)
                .orElse(null);
        if (men == null) return new org.springframework.security.core.userdetails.User(" ", " ", true, true, true, true,
                getGrantedAuthorities("user"));
        return new org.springframework.security.core.userdetails.User(men.getLogin(), men.getPassword(), true, true, true, true,
                getGrantedAuthorities(men.getRole().getName()));
    }

    private List<? extends GrantedAuthority> getGrantedAuthorities(String privilege) {
        return Arrays.asList(new SimpleGrantedAuthority(privilege));
    }
}
