package me.cozycosa.api.users.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import me.cozycosa.api.notes.DTO.NoteDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
