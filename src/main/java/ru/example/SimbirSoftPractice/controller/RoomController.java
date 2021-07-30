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
    @PreAuthorize("#roomServiceImpl.findById(room.id).creator.ban == false ") //TODO в spel-выражения я не уверен
    private Long savePublicRoom (@RequestBody RoomForm room){return roomService.savePublicRoom(room);}

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomServiceImpl.findById(room.id).creator.id == authentication.principal.id")
    private long update(@RequestBody RoomForm room){return  roomService.update(room);}

    @GetMapping("/all")
    private List<RoomVO> findAll () { return  roomService.findAll();}

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #roomServiceImpl.findById(room.id).creator.id == authentication.principal.id") //TODO в spel-выражения я не уверен
    private void delete(@PathVariable Long id){ roomService.delete(id);}

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MODERATOR') OR #roomServiceImpl.findById(room.id).creator.id == authentication.principal.id") //TODO в spel-выражения я не уверен
    private void addUser(@RequestBody RoomForm roomForm, @RequestBody UserForm userForm){roomService.addUser(roomForm,userForm);}

    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR #roomServiceImpl.findById(room.id).creator.id == authentication.principal.id") //TODO в spel-выражения я не уверен
    private void deleteUser(@RequestBody RoomForm roomForm, @RequestBody UserForm userForm){roomService.deleteUser(roomForm,userForm);}
}
