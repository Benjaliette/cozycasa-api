package me.cozycosa.api.users.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.cozycosa.api.homes.DTO.HomeDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private long id;
    private String username;
    private String email;

    @NotBlank(message = "Le mot de passe doit être renseigné")
    @Size(min = 8, max = 20, message = "Le mot de passe doit faire entre 8 et 20 caractères")
    private String password;

    private boolean admin;
    private List<HomeDto> homes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
