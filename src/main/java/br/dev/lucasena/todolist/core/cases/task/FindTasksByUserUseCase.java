package br.dev.lucasena.todolist.core.cases.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.repositories.ITaskRepository;

@Service
public class FindTasksByUserUseCase {
    @Autowired
    private ITaskRepository taskRepository;

    public List<Task> execute(UUID userId, String name) {
        if (name != null && !name.isEmpty()) {
            return taskRepository.findByUserIdAndNameContainingIgnoreCase(userId, name)
                    .orElseThrow(TaskNotFoundException::new);
        } else {
            return taskRepository.findByUserId(userId).orElseThrow(TaskNotFoundException::new);
        }
    }
}
