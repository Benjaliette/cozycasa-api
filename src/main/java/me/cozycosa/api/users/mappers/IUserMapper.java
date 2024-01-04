package me.cozycosa.api.users.mappers;

import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    public UserDto userEntityToDto(UserEntity userEntity);

    public UserEntity userDtoToEntity(UserDto userDto);
}
