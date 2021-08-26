package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.modelForm.MessageForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;
import ru.example.SimbirSoftPractice.servise.MessageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {
    MessageServiceImpl messageService;

    @PostMapping("")
    private MessageVO save (@RequestBody MessageForm messageForm){return messageService.save(messageForm);}

    @PutMapping("")
    private MessageVO update(@RequestBody MessageForm messageForm){return  messageService.update(messageForm);}

    @GetMapping("/all")
    private List<MessageVO> findAll () { return  messageService.findAll();}


    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ messageService.delete(id);}


    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public MessageVO change(MessageForm messageForm) {
        return  messageService.change(messageForm);
    }
}
