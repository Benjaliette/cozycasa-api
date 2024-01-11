package me.cozycosa.api.events.repositories;

import me.cozycosa.api.events.entities.EventEntity;
import me.cozycosa.api.notes.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "SELECT n FROM EventEntity n JOIN FETCH n.home h WHERE h.id = :homeId")
    List<EventEntity> findAll(Long homeId);

    @Query(value = "SELECT e FROM EventEntity e " +
            "JOIN FETCH e.home h " +
            "JOIN FETCH e.user u " +
            "WHERE e.id = :id " +
            "AND h.id = :homeId " +
            "AND u.email = ?#{ principal?.username }")
    Optional<EventEntity> findById(Long id, Long homeId);

    @Override
    EventEntity save(EventEntity entity);
}
