package me.cozycosa.api.homes.entities;

import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.events.entities.EventEntity;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.shared.BaseEntity;
import me.cozycosa.api.tasks.entities.TaskEntity;
import me.cozycosa.api.users.entities.UserEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "homes")
public class HomeEntity extends BaseEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Le foyer doit avoir un nom")
    @NotBlank(message = "Le foyer doit avoir un nom")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_homes",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> users = new ArrayList<>();

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<NoteEntity> notes;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EventEntity> events;

    @OneToMany(mappedBy = "home", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TaskEntity> tasks;
}
