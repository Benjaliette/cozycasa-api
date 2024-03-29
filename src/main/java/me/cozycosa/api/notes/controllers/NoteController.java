package me.cozycosa.api.notes.controllers;

import lombok.RequiredArgsConstructor;
import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.homes.mappers.HomeMapper;
import me.cozycosa.api.homes.services.HomeService;
import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.services.NoteService;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{homeId}/notes")
public class NoteController {

    @Autowired
    private NoteService service;

    @Autowired
    private UserService userService;

    @Autowired
    private HomeService homeService;

    @Autowired
    private HomeMapper homeMapper;

    @GetMapping()
    public ResponseEntity<List<NoteDto>> getAllNotes(@PathVariable Long homeId) {
        return new ResponseEntity<>(service.findAll(homeId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNotesById(@PathVariable Long id, @PathVariable Long homeId) throws Exception {
        return new ResponseEntity<>(service.findById(id, homeId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto note, @PathVariable Long homeId, Principal principal) {
        UserEntity currentUser = userService.getUserBy(principal.getName());
        return new ResponseEntity<>(service.create(note, homeId, currentUser), HttpStatus.CREATED);
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
