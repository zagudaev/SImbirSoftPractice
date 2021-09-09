package ru.example.SimbirSoftPractice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.example.SimbirSoftPractice.domain.model.Men;
import ru.example.SimbirSoftPractice.domain.modelDTO.MenDTO;

import java.util.List;


@Mapper
public interface MenMapper {

    MenMapper INSTANCE = Mappers.getMapper( MenMapper.class );


    MenDTO toMenDTO(Men men);

    Men toMen(MenDTO menDTO);


    Men updateMen(@MappingTarget Men men, MenDTO menDTO);

    List<MenDTO> toMenDTOList(List<Men> men);

    List<Men> toMenList(List<MenDTO> man);
    
}
