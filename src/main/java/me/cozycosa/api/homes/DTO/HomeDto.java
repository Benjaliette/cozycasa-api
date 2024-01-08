package me.cozycosa.api.homes.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.cozycosa.api.users.DTO.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class HomeDto {
    private Long id;
    private String name;
    private List<UserDto> users;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
