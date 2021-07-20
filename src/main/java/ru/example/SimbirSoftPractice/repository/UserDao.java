package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.SimbirSoftPractice.domain.model.User;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findById (Long id);
}

