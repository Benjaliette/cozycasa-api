package me.cozycosa.api.homes.mappers;

import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.notes.DTO.NoteDto;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import me.cozycosa.api.users.mappers.UserMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface HomeMapper {
    @Mapping(target = "users")
    public HomeDto homeEntityToDto(HomeEntity homeEntity);

    List<HomeDto> listEntityToListDto(List<HomeEntity> homeEntityList);


    @InheritInverseConfiguration
    @Mapping(target = "id", ignore = true)
    public HomeEntity homeDtoToEntity(HomeDto userDto);

    List<HomeEntity> listDtoToListEntity(List<HomeDto> homeDtoList);
}
