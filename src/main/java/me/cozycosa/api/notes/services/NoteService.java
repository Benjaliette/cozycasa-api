package me.cozycosa.api.notes.services;

import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.notes.mappers.INoteMapper;
import me.cozycosa.api.notes.mappers.NoteMapper;
import me.cozycosa.api.notes.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private INoteMapper mapper;

    public List<NoteDto> findAll() {
        repository.findAll().forEach(noteEntity -> {
            noteEntity.getId();
        });

        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(noteEntity -> noteEntity.getId()))
                .map(noteEntity -> mapper.entityToDto(noteEntity))
                .toList();
    }

    public NoteDto findById(Long id) {
        NoteEntity note = repository.findById(id).orElseThrow();

        return mapper.entityToDto(note);
    }

    public NoteDto create(NoteDto note) {
        NoteEntity noteToSave = mapper.dtoToEntity(note);

        NoteEntity savedNote = repository.save(noteToSave);

        return mapper.entityToDto(savedNote);
    }

    public NoteDto update(NoteDto newNote, Long id) {
        NoteEntity foundNote = repository.findById(id).map(note -> {
            note.setTitle(newNote.getTitle());
            note.setContent(newNote.getContent());
            return repository.save(note);
        }).orElseGet(() -> {
            NoteEntity newNoteEntity = mapper.dtoToEntity(newNote);
            newNoteEntity.setId(id);
            return repository.save(newNoteEntity);
        });

        return mapper.entityToDto(foundNote);
    }

    public String delete(Long id) {
        repository.deleteById(id);

        return "La note a été supprimée";
    }
}
