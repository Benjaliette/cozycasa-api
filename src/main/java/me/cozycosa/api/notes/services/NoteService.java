package me.cozycosa.api.notes.services;

import jakarta.transaction.Transactional;
import me.cozycosa.api.exceptions.RecordNotAllowedException;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.homes.repositories.HomeRepository;
import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.notes.mappers.NoteMapper;
import me.cozycosa.api.notes.repositories.NoteRepository;
import me.cozycosa.api.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private HomeRepository homeRepository;

    @Autowired
    private NoteMapper mapper;

    public List<NoteDto> findAll(Long homeId) {
        List<NoteEntity> noteEntityList = repository.findAll(homeId);

        return mapper.listEntityToListDto(noteEntityList);
    }

    public NoteDto findById(Long id, Long homeId) throws Exception {
        NoteEntity note = repository.findById(id, homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(), "Vous n'êtes pas à l'origine de cette note");
        });

        return mapper.entityToDto(note);
    }

    @Transactional
    public NoteDto create(NoteDto note, Long homeId, UserEntity currentUser) {
        NoteEntity noteToSave = mapper.dtoToEntity(note);
        noteToSave.setUser(currentUser);

        HomeEntity home = homeRepository.findById(homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "Le foyer n'a pas pu être récupéré");
        });
        noteToSave.setHome(home);

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
