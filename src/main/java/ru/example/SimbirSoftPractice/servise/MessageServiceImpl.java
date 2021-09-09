package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.SimbirSoftPractice.exception.ResponseException;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.modelDTO.MessageDTO;
import ru.example.SimbirSoftPractice.mappers.MessageMapper;
import ru.example.SimbirSoftPractice.repository.MessageDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;
import ru.example.SimbirSoftPractice.repository.MenDao;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageDao messageDao;
    private final MenDao menDao;
    private final RoomDao roomDao;
    //private final MessageMapper messageMapper;

    @Override
    @Transactional
    //@PreAuthorize("#messageServiceImpl.findById(messegeForm.id).man.ban == false ") //TODO в spel-выражения я не уверен
    public MessageDTO save(MessageDTO messageDTO) {
        Message message = new Message();
        message = MessageMapper.INSTANCE.toMessage(messageDTO);
        messageDao.save(message);
        return MessageMapper.INSTANCE.toMessageDTO(message);

    }

    @Override
    @Transactional
    public MessageDTO update(MessageDTO messageDTO) {
        Message message= messageDao.findById(messageDTO.getId()).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + messageDTO.getId()));

        message = MessageMapper.INSTANCE.updateMessage(messageDTO,message);
        messageDao.save(message);
        return MessageMapper.INSTANCE.toMessageDTO(message);
    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #messageServiceImpl.findById(id).room.creator.id == authentication.principal.id")//TODO в spel-выражения я не уверен
    public void delete(Long id) {
        Message massege= messageDao.findById(id).orElseThrow(() ->
             new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        messageDao.deleteById(id);


    }

    @Override
    @Transactional(readOnly = true)
    public MessageDTO findById(Long id) {
        Message massege = messageDao.findById(id).orElseThrow(() ->
                new ResponseException(HttpStatus.BAD_REQUEST, "Не найден сообщение с ID = " + id));
        MessageDTO messageDTO = MessageMapper.INSTANCE.toMessageDTO(massege);
        return messageDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> findAll() {
        List<Message> messageList = messageDao.findAll();
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message : messageList) {
            messageDTOList.add(MessageMapper.INSTANCE.toMessageDTO(message));
        }

        return messageDTOList;
    }

    @Override
    @Transactional
    public MessageDTO change(MessageDTO messageDTO) {
         Message message = messageDao.findById(messageDTO.getId()).get();
         if (messageDao.findById(messageDTO.getId()).orElse(null) != null){
             message = MessageMapper.INSTANCE.toMessage(messageDTO);
         }else{
             message = new Message();
             message = MessageMapper.INSTANCE.toMessage(messageDTO);
         }
        messageDao.save(message);


        return MessageMapper.INSTANCE.toMessageDTO(message);
    }
}
