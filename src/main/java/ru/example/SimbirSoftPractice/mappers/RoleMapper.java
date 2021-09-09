package ru.example.SimbirSoftPractice.mappers;

import org.mapstruct.factory.Mappers;
import ru.example.SimbirSoftPractice.domain.model.Role;
import ru.example.SimbirSoftPractice.domain.modelDTO.RoleDTO;

public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper( RoleMapper.class );

    RoleDTO toRoleDTO(Role message);

    Role toRole(RoleDTO messageDTO);
}
