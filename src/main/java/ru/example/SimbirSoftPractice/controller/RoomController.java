package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.Room;
import ru.example.SimbirSoftPractice.servise.RoomServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {
    RoomServiceImpl roomService;
    @PostMapping("")
    private Long save (@RequestBody Room room){return roomService.save(room);}

    @PutMapping("")
    private long update(@RequestBody Room room){return  roomService.update(room);}

    @GetMapping("/all")
    private List<Room> findAll () { return  roomService.findAll();}

    @DeleteMapping("/{id}")
    private void delete(@PathVariable long id){ roomService.delete(id);}
}
