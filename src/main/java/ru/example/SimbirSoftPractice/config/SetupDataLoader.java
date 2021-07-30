package ru.example.SimbirSoftPractice.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.User;
import ru.example.SimbirSoftPractice.repository.RoleDao;
import ru.example.SimbirSoftPractice.repository.UserDao;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private boolean alreadySetup = false;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private Role createRoleIfNotFound(String name) {
        Role role = roleDao.findByName(name).orElse(null);
        if (role == null) {
            role = new Role();
            role.setName(name);
            roleDao.save(role);
        }
        return role;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_MODERATOR");
        Role roleAdmin = createRoleIfNotFound("ROLE_ADMIN");
        User user = userDao.findByLogin("admin").orElse(null);
        if (user == null) {
            user = new User();
            user.setLogin("admin");
            user.setPassword(bCryptPasswordEncoder.encode("admin"));
            user.setBan(false);
            user.setRole(roleAdmin);
            userDao.save(user);
        }
        alreadySetup = true;
    }
}
