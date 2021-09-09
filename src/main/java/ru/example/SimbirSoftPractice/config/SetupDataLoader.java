package ru.example.SimbirSoftPractice.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.repository.RoleDao;
import ru.example.SimbirSoftPractice.repository.MenDao;
import ru.example.SimbirSoftPractice.repository.RoomDao;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final MenDao menDao;
    private final RoleDao roleDao;
    private  final RoomDao roomDao;
    private boolean alreadySetup = false;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private Role createRoleIfNotFound(String name) {
        Role role;
        role = roleDao.findByName(name).orElse(null);
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
        Men men = menDao.findByLogin("admin").orElse(null);
        if (men == null) {
            men = new Men();
            men.setLogin("admin");
            men.setPassword(bCryptPasswordEncoder.encode("admin"));
            men.setBan(false);
            men.setUsername("admin");
            men.setRole(roleAdmin);
            menDao.save(men);
        }
        Room room = roomDao.findByName("BOT").orElse(null);
        if (room == null){
            room = new Room();
            room.setName("BOT");
            room.setCreator(menDao.findByLogin("admin").get());
            room.setMen(menDao.findAll());
            roomDao.save(room);
        }
        alreadySetup = true;
    }
}
