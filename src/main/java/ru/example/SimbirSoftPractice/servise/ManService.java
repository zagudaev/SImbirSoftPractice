package ru.example.SimbirSoftPractice.servise;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.example.SimbirSoftPractice.domain.model.Man;
import ru.example.SimbirSoftPractice.domain.modelForm.ManForm;
import ru.example.SimbirSoftPractice.domain.modelVO.ManVO;

import java.util.List;

public interface ManService extends UserDetailsService {
    Long save (ManForm user);
    Long update (ManForm user);
    void delete (Long id);

    List<ManVO> findAll();
    Man findByLogin(String login);

    void ban(ManForm manForm);
    void unBan (ManForm manForm);

    void addModerator(ManForm manForm);
    void deleteModerator(ManForm manForm);


}
