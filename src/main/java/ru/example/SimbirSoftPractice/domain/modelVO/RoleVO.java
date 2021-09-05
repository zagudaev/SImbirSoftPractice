package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Role;
@Data
public class RoleVO {
    private Long id;

    private String name;

    public RoleVO (Role role){
        this.id = role.getId();
        this.name = role.getName();
    }
}
