package me.cozycosa.api.notes.DTO;

import lombok.*;
import me.cozycosa.api.users.DTO.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDto user;
}
