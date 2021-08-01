package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
import ru.example.SimbirSoftPractice.domain.modelForm.UserForm;
import ru.example.SimbirSoftPractice.domain.modelVO.RoomVO;
import ru.example.SimbirSoftPractice.servise.RoomServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {
    RoomServiceImpl roomService;
    @PostMapping("")
    private Long save (@RequestBody RoomForm room){return roomService.save(room);}

    @PutMapping("")

    private long update(@RequestBody RoomForm room){return  roomService.update(room);}

    @GetMapping("/all")
    private List<RoomVO> findAll () { return  roomService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ roomService.delete(id);}

  //  @PutMapping("")
  //
  //  private void addUser(@RequestBody RoomForm roomForm, @RequestBody UserForm userForm){roomService.addUser(roomForm,userForm);}

  //  @PutMapping("")
  //
  //  private void deleteUser(@RequestBody RoomForm roomForm, @RequestBody UserForm userForm){roomService.deleteUser(roomForm,userForm);}
}
