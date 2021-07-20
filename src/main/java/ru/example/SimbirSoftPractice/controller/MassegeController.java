package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.modelForm.MassegeForm;
import ru.example.SimbirSoftPractice.servise.MassegeServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/massge")
@AllArgsConstructor
public class MassegeController {
    MassegeServiceImpl massegeService;

    @PostMapping("")
    private Long save (@RequestBody MassegeForm massege){return massegeService.save(massege);}

    @PutMapping("")
    private long update(@RequestBody MassegeForm massege){return  massegeService.update(massege);}

    @GetMapping("/all")
    private List<Massege> findAll () { return  massegeService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id){ massegeService.delete(id);}
}
