package me.cozycosa.api.tasks.services;

import me.cozycosa.api.exceptions.RecordNotAllowedException;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.homes.repositories.HomeRepository;
import me.cozycosa.api.tasks.DTO.TaskDto;
import me.cozycosa.api.tasks.entities.TaskEntity;
import me.cozycosa.api.tasks.mappers.TaskMapper;
import me.cozycosa.api.tasks.repositories.TaskRepository;
import me.cozycosa.api.users.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    HomeRepository homeRepository;

    @Autowired
    TaskMapper taskMapper;

    public List<TaskDto> findAll(Long homeId) {
        List<TaskEntity> listTaskEntity = taskRepository.findAll(homeId);

        return taskMapper.listTaskEntityToListDto(listTaskEntity);
    }

    public TaskDto findById(Long id, Long homeId) {
        TaskEntity taskEntity = taskRepository.findById(id, homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "La tâche n'a pas pu être récupérée");
        });

        return taskMapper.taskEntityToDto(taskEntity);
    }

    public TaskDto create(TaskDto taskDto, UserEntity currentUser, Long homeId) {
        TaskEntity taskEntity = taskMapper.taskDtoToEntity(taskDto);

        taskEntity.setUser(currentUser);

        HomeEntity homeEntity = homeRepository.findById(homeId).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "Le foyer n'a pas pu être récupéré");
        });
        taskEntity.setHome(homeEntity);

        TaskEntity savedTask = taskRepository.save(taskEntity);

        return taskMapper.taskEntityToDto(savedTask);
    }

    public TaskDto update(TaskDto taskDto, Long id) {
        TaskEntity taskEntity = taskRepository.findById(id).orElseThrow(() -> {
            return new RecordNotAllowedException(HttpStatus.FORBIDDEN.value(),
                    "La tâche n'a pas pu être récupérée");
        });

        taskEntity.setTitle(taskDto.getTitle());
        taskEntity.setDone(taskDto.isDone());

        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return taskMapper.taskEntityToDto(updatedTask);
    }

    public String delete(Long id) {
        taskRepository.deleteById(id);

        return "La tâche a été supprimée";
    }
}
