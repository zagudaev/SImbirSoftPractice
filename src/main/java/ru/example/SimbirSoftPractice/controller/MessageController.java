package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelDTO.MessageDTO;
import ru.example.SimbirSoftPractice.domain.modelVO.MessageVO;
import ru.example.SimbirSoftPractice.servise.MessageServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {
    MessageServiceImpl messageService;

    @PostMapping("")
    private MessageDTO save (@RequestBody MessageDTO messageDTO){return messageService.save(messageDTO);}

    @PutMapping("")
    private MessageDTO update(@RequestBody MessageDTO messageDTO){return  messageService.update(messageDTO);}

    @GetMapping("/all")
    private List<MessageDTO> findAll () { return  messageService.findAll();}


    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ messageService.delete(id);}


    @MessageMapping("/changeMessage")
    @SendTo("/topic/activity")
    public MessageDTO change(MessageDTO messageDTO) {
        return  messageService.change(messageDTO);
    }
}
