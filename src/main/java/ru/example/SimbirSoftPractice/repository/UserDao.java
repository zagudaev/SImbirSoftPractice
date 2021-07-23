package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.User;

import java.util.Optional;
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findById (Long id);
}

