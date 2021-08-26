package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelForm.MessageForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;

import java.util.List;

public interface MessageService {
    MessageVO save (MessageForm messege);
    MessageVO update (MessageForm messege);
    void delete (Long id);
    MessageVO findById(Long id);

    List<MessageVO> findAll();
    MessageVO change(MessageForm messageForm);
}
