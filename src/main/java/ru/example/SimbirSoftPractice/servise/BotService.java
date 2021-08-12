package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelForm.MessegeForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessegeVO;

public interface BotService {
    MessegeVO messageАnalysis(MessegeForm messegeForm) ;
}
