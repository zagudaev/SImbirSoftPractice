package ru.example.SimbirSoftPractice.servise;

import ru.example.SimbirSoftPractice.domain.modelForm.MessegeForm;
import ru.example.SimbirSoftPractice.domain.modelVO.MassegeVO;

import java.util.List;

public interface MessegeService {
    Long save (MessegeForm messege);
    Long update (MessegeForm messege);
    void delete (Long id);
    MassegeVO findById(Long id);

    List<MassegeVO> findAll();
}
