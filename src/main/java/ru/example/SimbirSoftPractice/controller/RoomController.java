package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;
import ru.example.SimbirSoftPractice.domain.modelDTO.RoomDTO;
import ru.example.SimbirSoftPractice.servise.RoomServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {
    RoomServiceImpl roomService;
    @PostMapping("")
    private Long save (@RequestBody RoomDTO room){return roomService.save(room);}

    @PutMapping("")

    private long update(@RequestBody RoomDTO room){return  roomService.update(room);}

    @GetMapping("/all")
    private List<RoomDTO> findAll () { return  roomService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable Long id){ roomService.delete(id);}

   @PutMapping("/add")
   private void addUser(@RequestBody RoomDTO roomDTO, @RequestBody MenDTO menDTO){roomService.addUser(roomDTO, menDTO);}

   @PutMapping("/delete")
   private void deleteUser(@RequestBody RoomDTO roomDTO, @RequestBody MenDTO menDTO){roomService.deleteUser(roomDTO, menDTO);}
}
