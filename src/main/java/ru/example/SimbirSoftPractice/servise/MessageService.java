package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelDTO.MessageDTO;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;

import java.util.List;

public interface MessageService {
    MessageDTO save (MessageDTO messege);
    MessageDTO update (MessageDTO messege);
    void delete (Long id);
    MessageDTO findById(Long id);

    List<MessageDTO> findAll();
    MessageDTO change(MessageDTO messageDTO);
}
