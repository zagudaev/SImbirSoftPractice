package ru.example.SimbirSoftPractice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.example.SimbirSoftPractice.domain.modelForm.ManForm;
import ru.example.SimbirSoftPractice.domain.modelForm.RoomForm;
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

   @PutMapping("/add")
   private void addUser(@RequestBody RoomForm roomForm, @RequestBody ManForm manForm){roomService.addUser(roomForm,manForm);}

   @PutMapping("/delete")
   private void deleteUser(@RequestBody RoomForm roomForm, @RequestBody ManForm manForm){roomService.deleteUser(roomForm,manForm);}
}
