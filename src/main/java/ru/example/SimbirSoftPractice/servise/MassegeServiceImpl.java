package ru.example.SimbirSoftPractice.servise;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.example.SimbirSoftPractice.domain.model.Massege;
import ru.example.SimbirSoftPractice.domain.modelForm.MassegeForm;

import java.util.List;

@Service
@AllArgsConstructor
public class MassegeServiceImpl implements MassegeService {
    @Override
    public long save(MassegeForm messege) {
        return 0;
    }

    @Override
    public long update(MassegeForm messege) {
        return 0;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Massege> findAll() {
        return null;
    }
}
