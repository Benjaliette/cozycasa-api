package me.cozycosa.api.events.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.shared.BaseEntity;
import me.cozycosa.api.users.entities.UserEntity;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class EventEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "start_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "end_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endDate = LocalDateTime.now().plusHours(1);

    @Column(name = "title", nullable = false)
    @NotNull(message = "L'évènement doit avoir un titre")
    @NotBlank(message = "Le titre de l'évènement ne doit pas être vide")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private HomeEntity home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
