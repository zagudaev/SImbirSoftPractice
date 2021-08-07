package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.Man;

import java.util.Optional;
@Repository
public interface ManDao extends JpaRepository<Man, Long> {
    Optional<Man> findByLogin(String login);

    Optional<Man> findById (Long id);

}

