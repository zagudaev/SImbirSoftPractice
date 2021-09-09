package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelDTO.MessageDTO;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;

public interface BotService {
    MessageDTO messageАnalysis(MessageDTO messageDTO) ;
}
