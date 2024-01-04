package me.cozycosa.api.users.DTO;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserDto {
    private long id;
    private String username;
    private String email;
    private String password;
    private boolean admin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
