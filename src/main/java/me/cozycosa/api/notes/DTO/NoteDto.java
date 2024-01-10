package me.cozycosa.api.notes.DTO;

import lombok.*;
import me.cozycosa.api.homes.DTO.HomeDto;
import me.cozycosa.api.users.DTO.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
