package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;
import ru.example.SimbirSoftPractice.servise.MenServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class MenController {
    private final MenServiceImpl userService;

    @PostMapping("")
    private Long save (@RequestBody MenDTO user){return userService.save(user);}

    @PutMapping("")
    private Long update(@RequestBody MenDTO user){return  userService.update(user);}

    @GetMapping("/all")
    private List<MenDTO> findAll () { return  userService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ userService.delete(id);}

    @PutMapping("/ban")
    private  void  ban (@RequestBody MenDTO user){ userService.ban(user);}

    @PutMapping("/unBan")
    private  void  unBan (@RequestBody MenDTO user){ userService.unBan(user);}

     @PutMapping("/addModerator")
     private  void  addModerator(@RequestBody MenDTO user){userService.addModerator(user);}

     @PutMapping("/deleteModerator")
     private  void  deleteModerator(@RequestBody MenDTO user){userService.deleteModerator(user);}


}
