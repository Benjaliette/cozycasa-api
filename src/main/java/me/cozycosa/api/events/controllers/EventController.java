package me.cozycosa.api.events.controllers;

import me.cozycosa.api.events.DTO.EventDto;
import me.cozycosa.api.events.services.EventService;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/{homeId}/events")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<EventDto>> getAllEvents(@PathVariable Long homeId) {
        return new ResponseEntity<>(eventService.findAll(homeId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id, @PathVariable Long homeId) {
        return new ResponseEntity<>(eventService.findById(id, homeId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto, @PathVariable Long homeId,
                                                Principal principal) {
        UserEntity currentUser = userService.getUserBy(principal.getName());

        return new ResponseEntity<>(eventService.create(eventDto, currentUser, homeId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto, @PathVariable Long id) {
        return new ResponseEntity<>(eventService.update(eventDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
        return new ResponseEntity<>(eventService.delete(id), HttpStatus.OK);
    }
}
