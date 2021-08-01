package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.Man;

@Data
public class ManVO {

    private Long id;

    private String login;


    private String password;

    private String username;

    private boolean ban;

    private Role role;

    public ManVO(Man man){
        this.id = man.getId();
        this.login = man.getLogin();
        this.password = man.getPassword();
        this.username = man.getUsername();
        this.ban = man.isBan();
        this.role = man.getRole();

    }

}
