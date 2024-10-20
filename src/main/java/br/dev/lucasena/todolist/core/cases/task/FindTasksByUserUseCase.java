package br.dev.lucasena.todolist.core.cases.task;

import br.dev.lucasena.todolist.core.exceptions.task.TaskNotFoundException;
import br.dev.lucasena.todolist.domain.task.Task;
import br.dev.lucasena.todolist.repositories.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FindTasksByUserUseCase {
    @Autowired
    private ITaskRepository taskRepository;

    public List<Task> execute(UUID userId) {
        return taskRepository.findByUserId(userId).orElseThrow(TaskNotFoundException::new);
    }
}
