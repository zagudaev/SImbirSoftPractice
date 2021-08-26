package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.example.SimbirSoftPractice.exception.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.modelForm.MessageForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;
import ru.example.SimbirSoftPractice.repository.MessageDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.ManDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final ManDao manDao;
    private final RoomDao roomDao;

    @Override
    @Transactional
    @PreAuthorize("#messageServiceImpl.findById(messegeForm.id).man.ban == false ") //TODO в spel-выражения я не уверен
    public MessageVO save(MessageForm massegeForm) {
        if (messageDao.findById(massegeForm.getId()).orElse(null) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ошибка отправки сообщения  : " + massegeForm.getId() );
            }
        Message message = massegeForm.toMessege(manDao,roomDao);
        messageDao.save(message);
        return new MessageVO(message);

    }

    @Override
    @Transactional
    public MessageVO update(MessageForm messageForm) {
        Message message= messageDao.findById(messageForm.getId()).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + messageForm.getId()));

        message = messageForm.update(message, manDao,roomDao);
        messageDao.save(message);
        return new MessageVO(message);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #messageServiceImpl.findById(id).room.creator.id == authentication.principal.id")//TODO в spel-выражения я не уверен
    public void delete(Long id) {
        Message massege= messageDao.findById(id).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        messageDao.deleteById(id);


    }

    @Override
    @Transactional(readOnly = true)
    public MessageVO findById(Long id) {
        Message massege = messageDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        MessageVO messageVO = new MessageVO(massege);
        return messageVO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageVO> findAll() {
        return messageDao.findAll()
                .stream()
                .map(MessageVO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageVO change(MessageForm messageForm) {
         Message message = messageDao.findById(messageForm.getId()).get();
         if (messageDao.findById(messageForm.getId()).orElse(null) != null){
             message = messageForm.update(message, manDao,roomDao);
         }else{
             message = messageForm.toMessege(manDao,roomDao);
         }
        messageDao.save(message);


        return new MessageVO(message);
    }
}
