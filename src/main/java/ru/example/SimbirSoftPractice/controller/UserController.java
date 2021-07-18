package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.User;
import ru.example.SimbirSoftPractice.servise.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private UserServiceImpl userService;

    @PostMapping("")
    private Long save (@RequestBody User user){return userService.save(user);}

    @PutMapping("")
    private long update(@RequestBody User user){return  userService.update(user);}

    @GetMapping("/all")
    private List<User> findAll () { return  userService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id){ userService.delete(id);}


}
