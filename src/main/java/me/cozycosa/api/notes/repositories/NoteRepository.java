package me.cozycosa.api.notes.repositories;

import me.cozycosa.api.notes.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    @Query(value = "SELECT n FROM NoteEntity n JOIN FETCH n.home h WHERE h.id = :homeId")
    List<NoteEntity> findAll(Long homeId);

    @Override
    @Query(value = "SELECT n FROM NoteEntity n JOIN FETCH n.user u WHERE n.id = :id AND u.email = ?#{ principal?.username }")
    Optional<NoteEntity> findById(Long id);

    @Override
    NoteEntity save(NoteEntity entity);
}
