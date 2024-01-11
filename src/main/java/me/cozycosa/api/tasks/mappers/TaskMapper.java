package me.cozycosa.api.tasks.mappers;

import me.cozycosa.api.tasks.DTO.TaskDto;
import me.cozycosa.api.tasks.entities.TaskEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    public TaskDto taskEntityToDto(TaskEntity taskEntity);

    public List<TaskDto> listTaskEntityToListDto(List<TaskEntity> listTaskEntity);

    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    public TaskEntity taskDtoToEntity(TaskDto taskDto);

    public List<TaskEntity> listTaskDtoToListEntity(List<TaskDto> listTaskDto);
}
