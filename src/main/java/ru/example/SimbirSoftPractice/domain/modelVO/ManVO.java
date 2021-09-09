package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Men;

@Data
public class ManVO {

    private Long id;

    private String login;


    private String password;

    private String username;

    private boolean ban;

    private RoleVO role;

    public ManVO(Men men){
        this.id = men.getId();
        this.login = men.getLogin();
        this.password = men.getPassword();
        this.username = men.getUsername();
        this.ban = men.isBan();
        this.role = new RoleVO(men.getRole());

    }

}
