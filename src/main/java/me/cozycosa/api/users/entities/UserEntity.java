package me.cozycosa.api.users.entities;

import jakarta.persistence.*;
import lombok.*;
import me.cozycosa.api.events.entities.EventEntity;
import me.cozycosa.api.homes.entities.HomeEntity;
import me.cozycosa.api.notes.entities.NoteEntity;
import me.cozycosa.api.shared.BaseEntity;
import me.cozycosa.api.tasks.entities.TaskEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {
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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> notes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEntity> tasks;

    @ManyToMany(mappedBy = "users")
    private List<HomeEntity> homes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
