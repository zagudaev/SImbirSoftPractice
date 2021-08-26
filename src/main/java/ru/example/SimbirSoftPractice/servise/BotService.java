package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelForm.MessageForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;

public interface BotService {
    MessageVO messageАnalysis(MessageForm messageForm) ;
}
