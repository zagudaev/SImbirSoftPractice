package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.example.SimbirSoftPractice.util.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Messege;
import ru.example.SimbirSoftPractice.domain.modelForm.MessegeForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MassegeVO;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.UserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessegeServiceImpl implements MessegeService {
    private final MessegeDao messegeDao;
    private final UserDao userDao;
    private final RoomDao roomDao;

    @Override
    @Transactional
    @PreAuthorize("#messegeServiceImpl.findById(messegeForm.id).user.ban == false ") //TODO в spel-выражения я не уверен
    public Long save(MessegeForm massegeForm) {
        if (messegeDao.findById(massegeForm.getId()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка отправки сообщения  : " + massegeForm.getId() );
            }
        Messege massege = massegeForm.toMessege(userDao,roomDao);
        return messegeDao.save(massege).getId();

    }

    @Override
    @Transactional
    public Long update(MessegeForm messegeForm) {
        Messege massege= messegeDao.findById(messegeForm.getId()).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + messegeForm.getId()));

        massege = messegeForm.update(massege,userDao,roomDao);
        return messegeDao.save(massege).getId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #messegeServiceImpl.findById(id).room.creator.id == authentication.principal.id")//TODO в spel-выражения я не уверен
    public void delete(Long id) {
        Messege massege= messegeDao.findById(id).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        messegeDao.deleteById(id);


    }

    @Override
    @Transactional(readOnly = true)
    public MassegeVO findById(Long id) {
        Messege massege = messegeDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        MassegeVO massegeVO = new MassegeVO(massege);
        return massegeVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MassegeVO> findAll() {
        return messegeDao.findAll()
                .stream()
                .map(MassegeVO::new)
                .collect(Collectors.toList());
    }
}
