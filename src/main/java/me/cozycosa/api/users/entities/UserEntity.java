package me.cozycosa.api.users.entities;

import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.shared.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    @NotBlank(message = "Le nom d'utilisateur doit être renseigné")
    private String username;

    @Column(name = "email")
    @NotBlank(message = "L'email doit être renseigné")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "Le mot de passe doit être renseigné")
    @Size(min = 6, max = 20, message = "Le mot de passe doit être entre 6 et 20 caractères")
    private String password;

    @Column(name = "admin", nullable = false, columnDefinition = "boolean default false")
    private boolean admin;
}
