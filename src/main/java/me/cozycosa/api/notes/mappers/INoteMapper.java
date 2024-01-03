package me.cozycosa.api.notes.mappers;

import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface INoteMapper {
    NoteDto entityToDto(NoteEntity noteEntity);

    NoteEntity dtoToEntity(NoteDto noteDto);
}
