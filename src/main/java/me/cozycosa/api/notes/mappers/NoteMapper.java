package me.cozycosa.api.notes.mappers;

import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;

public class NoteMapper implements INoteMapper {
    @Override
    public NoteDto entityToDto(NoteEntity noteEntity) {
        if (noteEntity == null) {
            return null;
        }

        NoteDto noteDto = NoteDto.builder()
                .id(noteEntity.getId())
                .title(noteEntity.getTitle())
                .content(noteEntity.getContent())
                .createdAt(noteEntity.getCreatedAt())
                .updatedAt(noteEntity.getUpdatedAt())
                .build();

        return noteDto;
    }

    @Override
    public NoteEntity dtoToEntity(NoteDto noteDto) {
        if (noteDto == null) {
            return null;
        }

        NoteEntity noteEntity = NoteEntity.builder()
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .build();

        return noteEntity;
    }
}
