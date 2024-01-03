package me.cozycosa.api.notes.controllers;

import lombok.RequiredArgsConstructor;
import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping()
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNotesById(@PathVariable Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto note) {
        return new ResponseEntity<>(service.create(note), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto note, @PathVariable Long id) {
        return new ResponseEntity<>(service.update(note, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
