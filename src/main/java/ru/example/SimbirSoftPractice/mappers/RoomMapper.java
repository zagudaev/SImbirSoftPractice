package ru.example.SimbirSoftPractice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.example.SimbirSoftPractice.domain.model.Room;
import ru.example.SimbirSoftPractice.domain.modelDTO.RoomDTO;

@Mapper(uses = {MenMapper.class,MessageMapper.class})
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper( RoomMapper.class );

   @Mappings({
           @Mapping(target = "creator", source = "creator.id"),
           @Mapping(target = "men", source = "men"),
           @Mapping(target = "messages", source = "messages"),
   })
    RoomDTO toRoomDTO(Room room);

    @Mappings({
            @Mapping(target = "creator.id", source = "creator"),
            @Mapping(target = "men", source = "men"),
            @Mapping(target = "messages", source = "messages"),
    })
    Room toRoom(RoomDTO roomDTO);

    @Mappings({
            @Mapping(target = "creator.id", source = "roomDTO.creator"),
    })
    Room updateRoom(RoomDTO roomDTO,@MappingTarget Room room);

}
