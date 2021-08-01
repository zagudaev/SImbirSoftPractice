package ru.example.SimbirSoftPractice.domain.modelForm;

import lombok.Data;
import lombok.Getter;
import ru.example.SimbirSoftPractice.domain.model.Man;

import javax.validation.constraints.NotBlank;

@Data
@Getter
public class ManForm {
    private Long id;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String username;

    private boolean ban;


    public Man toUser(){
        Man man = new Man();
        man = update(man);
        return man;
    }
    public Man update(Man man){
        man.setLogin(login);
        man.setPassword(password);
        man.setUsername(username);
        man.setBan(ban);
        return man;
    }

}
