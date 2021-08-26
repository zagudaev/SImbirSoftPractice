package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.SimbirSoftPractice.domain.modelForm.MessageForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;
import ru.example.SimbirSoftPractice.servise.BotServiceImpl;

@RestController
@RequestMapping("/room/bot")
@AllArgsConstructor
public class BotController {
    BotServiceImpl botService;
    @PostMapping("")
    private MessageVO messageАnalysis (@RequestBody MessageForm messageForm){ return botService.messageАnalysis(messageForm);}
}
