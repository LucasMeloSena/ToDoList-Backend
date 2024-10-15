package br.dev.lucasena.todolist.repositories;

import br.dev.lucasena.todolist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(UUID userId);
}
