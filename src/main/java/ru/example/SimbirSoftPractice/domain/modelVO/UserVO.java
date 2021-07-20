package ru.example.SimbirSoftPractice.domain.modelVO;

import lombok.Data;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.User;

import javax.persistence.*;

@Data
public class UserVO  {

    private Long id;

    private String login;


    private String password;

    private String username;

    private boolean ban;

    private Role role;

    public UserVO(User user){
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.ban = user.isBan();
        this.role = user.getRole();

    }

}
