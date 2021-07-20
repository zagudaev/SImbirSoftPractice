package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.modelForm.MassegeForm;

import java.util.List;

public interface MassegeService {
    long save (MassegeForm messege);
    long update (MassegeForm messege);
    void delete (long id);

    List<Massege> findAll();
}
