package ru.example.SimbirSoftPractice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.example.SimbirSoftPractice.domain.model.Message;
import ru.example.SimbirSoftPractice.domain.modelDTO.MessageDTO;

@Mapper//(uses = {ManMapper.class,RoomMapper.class})
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper( MessageMapper.class );

    @Mappings({
            @Mapping(target = "men", source = "men.id"),
            @Mapping(target = "room", source = "room.id"),
    })
    MessageDTO toMessageDTO(Message message);

    @Mappings({
            @Mapping(target = "men.id", source = "men"),
            @Mapping(target = "room.id", source = "room"),
    })
    Message toMessage(MessageDTO messageDTO);

    @Mappings({
            @Mapping(target = "men.id", source = "messageDTO.men"),
            @Mapping(target = "room.id", source = "messageDTO.room"),
    })
    Message updateMessage(MessageDTO messageDTO,@MappingTarget Message message);

}
