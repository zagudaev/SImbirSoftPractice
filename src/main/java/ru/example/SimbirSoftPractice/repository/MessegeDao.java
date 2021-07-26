package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.Messege;

import java.util.Optional;

@Repository
public interface MessegeDao extends JpaRepository<Messege, Long> {
    Optional<Messege> findById (Long id);
}
