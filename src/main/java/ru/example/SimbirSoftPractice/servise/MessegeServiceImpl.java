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
import ru.example.SimbirSoftPractice.domain.modelVO.MessegeVO;
import ru.example.SimbirSoftPractice.repository.MessegeDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.ManDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessegeServiceImpl implements MessegeService {
    private final MessegeDao messegeDao;
    private final ManDao manDao;
    private final RoomDao roomDao;

    @Override
    @Transactional
    @PreAuthorize("#messegeServiceImpl.findById(messegeForm.id).man.ban == false ") //TODO в spel-выражения я не уверен
    public Long save(MessegeForm massegeForm) {
        if (messegeDao.findById(massegeForm.getId()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка отправки сообщения  : " + massegeForm.getId() );
            }
        Messege massege = massegeForm.toMessege(manDao,roomDao);
        return messegeDao.save(massege).getId();

    }

    @Override
    @Transactional
    public Long update(MessegeForm messegeForm) {
        Messege massege= messegeDao.findById(messegeForm.getId()).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + messegeForm.getId()));

        massege = messegeForm.update(massege, manDao,roomDao);
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
    public MessegeVO findById(Long id) {
        Messege massege = messegeDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        MessegeVO messegeVO = new MessegeVO(massege);
        return messegeVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessegeVO> findAll() {
        return messegeDao.findAll()
                .stream()
                .map(MessegeVO::new)
                .collect(Collectors.toList());
    }
}
