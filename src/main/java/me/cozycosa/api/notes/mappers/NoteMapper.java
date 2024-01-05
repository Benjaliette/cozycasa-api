package me.cozycosa.api.notes.mappers;

import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.shared.CycleAvoidingMappingContext;
import me.cozycosa.api.users.mappers.UserMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface NoteMapper {
    @Mapping(target = "user")
    NoteDto entityToDto(NoteEntity noteEntity);

    List<NoteDto> listEntityToListDto(List<NoteEntity> noteEntityList);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "user", ignore = true)
    NoteEntity dtoToEntity(NoteDto noteDto);

    List<NoteEntity> listDtoToListEntity(List<NoteDto> noteDtoList);
}
