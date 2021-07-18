package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.Massege;
import ru.example.SimbirSoftPractice.domain.User;

import java.util.List;

public interface MassegeService {
    long save (Massege messege);
    long update (Massege messege);
    void delete (long id);

    List<Massege> findAll();
}
