package me.cozycosa.api.notes.entities;

import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.shared.BaseEntity;

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

    @Column(name="title")
    private String title;

    @Column(nullable = false, name = "content")
    @NotNull(message = "La note doit avoir au moins un texte")
    private String content;
}
