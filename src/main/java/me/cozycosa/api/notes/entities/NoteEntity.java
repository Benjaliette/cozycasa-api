package me.cozycosa.api.notes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.shared.BaseEntity;
import me.cozycosa.api.users.entities.UserEntity;
import org.hibernate.annotations.Cascade;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class NoteEntity extends BaseEntity {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(nullable = false, name = "content")
    @NotNull(message = "La note doit avoir au moins un texte")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "home_id")
    private HomeEntity home;
}
