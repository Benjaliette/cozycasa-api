package me.cozycosa.api.users.mappers;

import me.cozycosa.api.notes.mappers.NoteMapper;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import org.mapstruct.*;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "notes", ignore = true)
    public UserDto userEntityToDto(UserEntity userEntity);


    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    public UserEntity userDtoToEntity(UserDto userDto);
}
