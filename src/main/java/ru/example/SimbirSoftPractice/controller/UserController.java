package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelForm.ManForm;
import ru.example.SimbirSoftPractice.domain.modelVO.ManVO;
import ru.example.SimbirSoftPractice.servise.ManServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final ManServiceImpl userService;

    @PostMapping("")
    private Long save (@RequestBody ManForm user){return userService.save(user);}

    @PutMapping("")
    private Long update(@RequestBody ManForm user){return  userService.update(user);}

    @GetMapping("/all")
    private List<ManVO> findAll () { return  userService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ userService.delete(id);}

    @PutMapping("/ban")
    private  void  ban (@RequestBody ManForm user){ userService.ban(user);}

    @PutMapping("/unBan")
    private  void  unBan (@RequestBody ManForm user){ userService.unBan(user);}

     @PutMapping("/addModerator")
     private  void  addModerator(@RequestBody ManForm user){userService.addModerator(user);}

     @PutMapping("/deleteModerator")
     private  void  deleteModerator(@RequestBody ManForm user){userService.addModerator(user);}


}
