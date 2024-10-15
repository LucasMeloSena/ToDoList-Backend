package br.dev.lucasena.todolist.core.cases.task;

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

    public List<Task> execute() {
        String userId = "9fe6d793-0a6e-4ff3-959b-23e08da2131c";
        return taskRepository.findByUserId(UUID.fromString(userId));
    }
}
