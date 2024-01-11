package me.cozycosa.api.tasks.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TaskDto {
    private long id;
    private String title;
    private boolean done;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
