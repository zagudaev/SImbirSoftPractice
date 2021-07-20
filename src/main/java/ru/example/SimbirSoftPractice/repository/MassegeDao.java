package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.example.SimbirSoftPractice.domain.model.Massege;

import java.util.Optional;

public interface MassegeDao extends JpaRepository<Massege, Long> {
    Optional<Massege> findById (Long id);
}
