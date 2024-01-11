package me.cozycosa.api.tasks.controllers;

import me.cozycosa.api.tasks.DTO.TaskDto;
import me.cozycosa.api.tasks.services.TaskService;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/{homeId}/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getAllTasks(@PathVariable Long homeId) {
        return new ResponseEntity<>(taskService.findAll(homeId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id, @PathVariable Long homeId) {
        return new ResponseEntity<>(taskService.findById(id, homeId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto, @PathVariable Long homeId,
                                              Principal principal) {
        UserEntity currentUser = userService.getUserBy(principal.getName());
        return new ResponseEntity<>(taskService.create(taskDto, currentUser, homeId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        return new ResponseEntity<>(taskService.update(taskDto, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.delete(id), HttpStatus.OK);
    }
}
