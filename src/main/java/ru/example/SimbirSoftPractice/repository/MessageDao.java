package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.Message;

import java.util.Optional;

@Repository
public interface MessageDao extends JpaRepository<Message, Long> {
    Optional<Message> findById (Long id);
}
