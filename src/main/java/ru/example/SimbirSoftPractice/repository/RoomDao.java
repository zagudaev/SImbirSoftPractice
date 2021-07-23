package ru.example.SimbirSoftPractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.SimbirSoftPractice.domain.model.Room;

import java.util.Optional;
@Repository
public interface RoomDao extends JpaRepository<Room, Long> {
    Optional<Room> findById (Long id);
}
