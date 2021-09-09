package ru.example.SimbirSoftPractice.servise;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;

import java.util.List;

public interface MenService extends UserDetailsService {
    Long save (MenDTO user);
    Long update (MenDTO user);
    void delete (Long id);

    List<MenDTO> findAll();
    Men findByLogin(String login);

    void ban(MenDTO menDTO);
    void unBan (MenDTO menDTO);

    void addModerator(MenDTO menDTO);
    void deleteModerator(MenDTO menDTO);

    void commandRename(MenDTO menDTO, String newUsername);

}
