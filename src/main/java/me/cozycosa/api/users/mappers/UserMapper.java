package me.cozycosa.api.users.mappers;

import me.cozycosa.api.users.DTO.UserDto;
import me.cozycosa.api.users.entities.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

public class UserMapper implements IUserMapper {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto userEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .admin(userEntity.isAdmin())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    @Override
    public UserEntity userDtoToEntity(UserDto userDto) {
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        return UserEntity.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(hashedPassword)
                .admin(userDto.isAdmin())
                .build();
    }
}
