package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.domain.modelVO.UserVO;
import ru.example.SimbirSoftPractice.servise.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("")
    private Long save (@RequestBody UserForm user){return userService.save(user);}

    @PutMapping("")
    private Long update(@RequestBody UserForm user){return  userService.update(user);}

    @GetMapping("/all")
    private List<UserVO> findAll () { return  userService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ userService.delete(id);}

  //  @PutMapping("")
  //
  //  private  void  ban (@RequestBody UserForm user){ userService.ban(user);}

 //  @PutMapping("")
 //  @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') ")
 //  private  void  unBan (@RequestBody UserForm user){ userService.unBan(user);}

  //  @PutMapping("")
  //
  //  private  void  addModerator(@RequestBody UserForm user){userService.addModerator(user);}

  //  @PutMapping("")
  //  @PreAuthorize("hasRole('ROLE_ADMIN') ")
  //  private  void  deleteModerator(@RequestBody UserForm user){userService.addModerator(user);}


}
