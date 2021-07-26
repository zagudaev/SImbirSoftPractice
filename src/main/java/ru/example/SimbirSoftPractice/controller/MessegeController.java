package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelForm.MessegeForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MassegeVO;
import ru.example.SimbirSoftPractice.servise.MessegeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/messege")
@AllArgsConstructor
public class MessegeController {
    MessegeServiceImpl messegeService;

    @PostMapping("")
    private Long save (@RequestBody MessegeForm massege){return messegeService.save(massege);}

    @PutMapping("")
    private long update(@RequestBody MessegeForm massege){return  messegeService.update(massege);}

    @GetMapping("/all")
    private List<MassegeVO> findAll () { return  messegeService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ messegeService.delete(id);}
}
