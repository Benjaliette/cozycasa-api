package me.cozycosa.api.tasks.entities;

import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.shared.BaseEntity;
import me.cozycosa.api.users.entities.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "La tâche doit avoir un titre")
    @NotBlank(message = "Le titre de la tâche ne doit pas être vide")
    private String title;

    @Column(name = "done", nullable = false)
    private boolean done = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_id")
    private HomeEntity home;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
