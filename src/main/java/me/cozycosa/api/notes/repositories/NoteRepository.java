package me.cozycosa.api.notes.repositories;

import me.cozycosa.api.notes.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {
    @Override
    @Query(value = "SELECT n FROM NoteEntity n")
    List<NoteEntity> findAll();

    @Override
    Optional<NoteEntity> findById(Long aLong);

    @Override
    NoteEntity save(NoteEntity entity);
}
