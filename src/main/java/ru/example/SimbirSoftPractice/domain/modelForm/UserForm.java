package ru.example.SimbirSoftPractice.domain.modelForm;

import lombok.Data;
import lombok.Getter;
import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.model.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@Getter
public class UserForm {
    private Long id;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String username;

    private boolean ban;


    public User toUser(){
        User user = new User();
        user = update(user);
        return user;
    }
    public User update(User user){
        user.setLogin(login);
        user.setPassword(password);
        user.setUsername(username);
        user.setBan(ban);
        return user;
    }

}
