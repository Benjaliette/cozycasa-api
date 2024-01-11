package me.cozycosa.api.tasks.repositories;

import me.cozycosa.api.events.entities.EventEntity;
import me.cozycosa.api.tasks.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query(value = "SELECT n FROM TaskEntity n JOIN FETCH n.home h WHERE h.id = :homeId")
    List<TaskEntity> findAll(Long homeId);

    @Query(value = "SELECT n FROM TaskEntity n " +
            "JOIN FETCH n.home h " +
            "JOIN FETCH n.user u " +
            "WHERE n.id = :id " +
            "AND h.id = :homeId " +
            "AND u.email = ?#{ principal?.username }")
    Optional<TaskEntity> findById(Long id, Long homeId);

    @Override
    TaskEntity save(TaskEntity entity);
}
