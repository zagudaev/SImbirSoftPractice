package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelForm.MessegeForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MessegeVO;
import ru.example.SimbirSoftPractice.servise.MessegeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/messege")
@AllArgsConstructor
public class MessegeController {
    MessegeServiceImpl messegeService;

    @PostMapping("")

    private Long save (@RequestBody MessegeForm messegeForm){return messegeService.save(messegeForm);}

    @PutMapping("")
    private long update(@RequestBody MessegeForm messegeForm){return  messegeService.update(messegeForm);}

    @GetMapping("/all")
    private List<MessegeVO> findAll () { return  messegeService.findAll();}


    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ messegeService.delete(id);}
}
