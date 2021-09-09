package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.Men;

import java.util.Optional;
@Repository
public interface MenDao extends JpaRepository<Men, Long> {
    Optional<Men> findByLogin(String login);

    Optional<Men> findById (Long id);

    void deleteById (Long id);

}

