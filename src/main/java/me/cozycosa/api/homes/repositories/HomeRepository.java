package me.cozycosa.api.homes.repositories;

import me.cozycosa.api.homes.entities.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HomeRepository extends JpaRepository<HomeEntity, Long> {
    @Override
    @Query("SELECT h FROM HomeEntity h JOIN FETCH h.users u WHERE u.email = ?#{ principal?.username }")
    List<HomeEntity> findAll();

    @Override
    @Query("SELECT h FROM HomeEntity h JOIN FETCH h.users u WHERE h.id = :id AND u.email = ?#{ principal?.username }")
    Optional<HomeEntity> findById(Long id);

    @Override
    void deleteById(Long id);
}
