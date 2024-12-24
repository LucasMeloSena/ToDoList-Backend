package br.dev.lucasena.todolist.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.dev.lucasena.todolist.domain.task.Task;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID> {
    Optional<List<Task>> findByUserId(UUID userId);

    Optional<List<Task>> findByUserIdAndNameContainingIgnoreCase(UUID userId, String name);
}
