package me.cozycosa.api.events.mappers;

import me.cozycosa.api.events.DTO.EventDto;
import me.cozycosa.api.events.entities.EventEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    public EventDto eventEntityToDto(EventEntity eventEntity);

    public List<EventDto> listEventEntityToListDto(List<EventEntity> listEventEntity);

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id", ignore = true)
    public EventEntity eventDtoToEntity(EventDto eventDto);

    public List<EventEntity> listEventDtoToListEntity(List<EventDto> listEventDto);
}
