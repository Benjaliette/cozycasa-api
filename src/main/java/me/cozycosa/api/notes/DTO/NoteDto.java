package me.cozycosa.api.notes.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Value
@Builder
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
