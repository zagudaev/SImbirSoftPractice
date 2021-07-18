package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.Massege;
import ru.example.SimbirSoftPractice.servise.MassegeServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/massge")
@AllArgsConstructor
public class MassegeController {
    MassegeServiceImpl massegeService;

    @PostMapping("")
    private Long save (@RequestBody Massege massege){return massegeService.save(massege);}

    @PutMapping("")
    private long update(@RequestBody Massege massege){return  massegeService.update(massege);}

    @GetMapping("/all")
    private List<Massege> findAll () { return  massegeService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id){ massegeService.delete(id);}
}
